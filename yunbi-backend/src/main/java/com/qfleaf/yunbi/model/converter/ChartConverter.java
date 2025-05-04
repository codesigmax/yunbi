package com.qfleaf.yunbi.model.converter;

import com.qfleaf.yunbi.model.ChartAnalysis;
import com.qfleaf.yunbi.model.response.ChartDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChartConverter {
    public ChartDetailsResponse toViewObj(ChartAnalysis entity) {
        if (entity == null) {
            return null;
        }
        return ChartDetailsResponse.builder()
                .id(entity.getId())
                .goal(entity.getGoal())
                .data(entity.getData())
                .type(entity.getType())
                .generateChart(entity.getGenerateChart())
                .generateConclusion(entity.getGenerateConclusion())
                .createTime(entity.getCreateTime())
                .build();
    }
}
