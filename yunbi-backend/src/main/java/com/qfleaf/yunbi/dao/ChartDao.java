package com.qfleaf.yunbi.dao;

import com.qfleaf.yunbi.dao.mapper.ChartMapper;
import com.qfleaf.yunbi.model.ChartAnalysis;
import com.qfleaf.yunbi.model.converter.ChartConverter;
import com.qfleaf.yunbi.model.response.ChartDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChartDao {
    private final ChartMapper chartMapper;
    private final ChartConverter chartConverter;

    public Optional<ChartDetailsResponse> getChartDetails(Long id) {
        ChartAnalysis chartAnalysis = chartMapper.selectById(id);
        return Optional.ofNullable(chartConverter.toViewObj(chartAnalysis));
    }

    public void save(ChartAnalysis chartAnalysis) {
        chartMapper.insert(chartAnalysis);
    }
}
