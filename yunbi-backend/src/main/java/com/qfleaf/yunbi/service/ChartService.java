package com.qfleaf.yunbi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qfleaf.yunbi.model.ChartAnalysis;
import com.qfleaf.yunbi.model.request.AnalyzeRequest;
import com.qfleaf.yunbi.model.response.ChartDetailsResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ChartService extends IService<ChartAnalysis> {
    ChartDetailsResponse getViewObjById(Long id);

    void genChart(MultipartFile file, AnalyzeRequest analyzeRequest);
}
