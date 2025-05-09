package com.qfleaf.yunbi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfleaf.yunbi.common.ResultCode;
import com.qfleaf.yunbi.common.exception.BusinessException;
import com.qfleaf.yunbi.common.util.FileConverterUtils;
import com.qfleaf.yunbi.dao.ChartDao;
import com.qfleaf.yunbi.dao.UserDao;
import com.qfleaf.yunbi.dao.mapper.ChartMapper;
import com.qfleaf.yunbi.model.ChartAnalysis;
import com.qfleaf.yunbi.model.User;
import com.qfleaf.yunbi.model.request.AnalyzeRequest;
import com.qfleaf.yunbi.model.response.ChartDetailsResponse;
import com.qfleaf.yunbi.service.ChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChartServiceImpl extends ServiceImpl<ChartMapper, ChartAnalysis> implements ChartService {
    private final ChartDao chartDao;
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final UserDao userDao;

    @Override
    public ChartDetailsResponse getViewObjById(Long id) {
        return chartDao.getChartDetails(id)
                .orElseThrow(() -> new BusinessException(ResultCode.NOT_FOUND.getCode(), ResultCode.NOT_FOUND.getMessage()));
    }

    @Override
    public void genChart(MultipartFile file, AnalyzeRequest analyzeRequest) {
        String dataCsv;
        String goal = analyzeRequest.getGoal();

        try {
            dataCsv = FileConverterUtils.convertToCsv(file);
        } catch (IOException e) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), e.getMessage());
        }
        String request = "data:" +
                dataCsv +
                "\ngoal:" +
                goal;
        String res = Objects.requireNonNull(chatClient
                        .prompt()
                        .user(request)
                        .call()
                        .content())
                .replace("```json", "")
                .replace("```", "");
        log.debug("生成数据: {}", res);

        ChartAnalysis chartAnalysis;
        try {
            chartAnalysis = objectMapper.readValue(res, ChartAnalysis.class);
            // todo optimize
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<Long> currentUserId = Optional.empty();
            if (authentication != null) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String username = userDetails.getUsername();
                currentUserId = userDao.findByUsernameOrPhoneOrEmail(username).map(User::getId);
            }
            // build data row
            chartAnalysis.setGoal(goal);
            chartAnalysis.setData(dataCsv);
            chartAnalysis.setUserId(currentUserId.orElse(null));
            log.debug("row ===> {}", chartAnalysis);
            chartDao.save(chartAnalysis);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
