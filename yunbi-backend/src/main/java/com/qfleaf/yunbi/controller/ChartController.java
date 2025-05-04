package com.qfleaf.yunbi.controller;

import com.qfleaf.yunbi.common.ApiResponse;
import com.qfleaf.yunbi.model.response.ChartDetailsResponse;
import com.qfleaf.yunbi.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/charts")
@RequiredArgsConstructor
public class ChartController {
    private final ChartService chartService;

    @GetMapping("/{id}")
    public ApiResponse<ChartDetailsResponse> showChart(@PathVariable("id") Long id) {
        ChartDetailsResponse vo = chartService.getViewObjById(id);
        return ApiResponse.success(vo);
    }
}
