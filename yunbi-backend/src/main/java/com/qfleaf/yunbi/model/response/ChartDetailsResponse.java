package com.qfleaf.yunbi.model.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qfleaf.yunbi.model.enums.ChartType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ChartDetailsResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String goal;
    private String data;
    private ChartType type;
    private Map<String, Object> generateChart;
    private String generateConclusion;
    private java.sql.Timestamp createTime;
}
