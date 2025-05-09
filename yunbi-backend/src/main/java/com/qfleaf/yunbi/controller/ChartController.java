package com.qfleaf.yunbi.controller;

import com.qfleaf.yunbi.common.ApiResponse;
import com.qfleaf.yunbi.model.request.AnalyzeRequest;
import com.qfleaf.yunbi.model.response.ChartDetailsResponse;
import com.qfleaf.yunbi.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/gen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Void> genChart(@RequestPart("file") MultipartFile file,
                                      @RequestPart("data") AnalyzeRequest analyzeRequest) {
        chartService.genChart(file, analyzeRequest);
        return ApiResponse.success();
    }
}
