package com.qfleaf.yunbi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfleaf.yunbi.common.ResultCode;
import com.qfleaf.yunbi.common.exception.BusinessException;
import com.qfleaf.yunbi.dao.ChartDao;
import com.qfleaf.yunbi.dao.mapper.ChartMapper;
import com.qfleaf.yunbi.model.ChartAnalysis;
import com.qfleaf.yunbi.model.response.ChartDetailsResponse;
import com.qfleaf.yunbi.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChartServiceImpl extends ServiceImpl<ChartMapper, ChartAnalysis> implements ChartService {
    private final ChartDao chartDao;

    @Override
    public ChartDetailsResponse getViewObjById(Long id) {
        return chartDao.getChartDetails(id)
                .orElseThrow(() -> new BusinessException(ResultCode.NOT_FOUND.getCode(), ResultCode.NOT_FOUND.getMessage()));
    }
}
