package com.qfleaf.yunbi.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qfleaf.yunbi.common.handler.EnhancedJsonbTypeHandler;
import com.qfleaf.yunbi.model.enums.ChartType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("chart_analysis")
public class ChartAnalysis {
    @TableId
    private Long id;
    private String goal;
    private String data;
    private ChartType type;
    @TableField(typeHandler = EnhancedJsonbTypeHandler.class)
    private Map<String, Object> generateChart;
    private String generateConclusion;
    private java.sql.Timestamp createTime;
    private java.sql.Timestamp updateTime;
    private java.sql.Timestamp deleteTime;
    private Long userId;
}
