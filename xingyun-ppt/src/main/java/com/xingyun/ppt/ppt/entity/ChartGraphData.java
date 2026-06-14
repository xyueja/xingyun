package com.xingyun.ppt.ppt.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * ChartGraphData
 *
 * @author yxuej
 * @since 2026-06-14
 */
@Getter
@Setter
public class ChartGraphData extends SlideConfig {
    private ChartType chartType;

    // 图表数据
    Map<String, SeriesData> seriesDataMap = new HashMap<>();

    public static ChartGraphData of(int index, String title, ChartType chartTypes) {
        ChartGraphData chartData = new ChartGraphData().build(index, title, SlideConfigType.CHART);
        chartData.setChartType(chartTypes);
        return chartData;
    }

    public ChartGraphData setSeries(SeriesData series) {
        return this.setSeries(ChartTypes.BAR, series);
    }

    public ChartGraphData setSeries(ChartTypes chartType, SeriesData series) {
        seriesDataMap.put(chartType.name(), series);
        return this;
    }
}
