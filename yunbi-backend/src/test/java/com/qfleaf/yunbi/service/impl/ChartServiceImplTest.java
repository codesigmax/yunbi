package com.qfleaf.yunbi.service.impl;

import com.qfleaf.yunbi.model.response.ChartDetailsResponse;
import com.qfleaf.yunbi.service.ChartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChartServiceImplTest {

    @Autowired
    private ChartService chartService;

    @Test
    void getViewObjById() {
        ChartDetailsResponse view = chartService.getViewObjById(1919958371541323778L);
        System.out.println(view);
    }

    @Test
    void getChart() {
//        chartService.genChart(file, analyzeRequest);
    }
}