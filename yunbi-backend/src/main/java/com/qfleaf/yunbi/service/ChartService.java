package com.qfleaf.yunbi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qfleaf.yunbi.model.ChartAnalysis;
import com.qfleaf.yunbi.model.response.ChartDetailsResponse;

public interface ChartService extends IService<ChartAnalysis> {
    ChartDetailsResponse getViewObjById(Long id);
}
