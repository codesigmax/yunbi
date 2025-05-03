package com.qfleaf.yunbi.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qfleaf.yunbi.model.enums.ChartType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("chart_analysis")
public class ChartAnalysis {
    @TableId
    private Long id;
    private String goal;
    private String data;
    private ChartType type;
    private String generateChart;
    private String generateConclusion;
    private java.sql.Timestamp createTime;
    private java.sql.Timestamp updateTime;
    private java.sql.Timestamp deleteTime;
    private Long userId;
}
