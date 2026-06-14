package com.xingyun.ppt.ppt.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * ChartData
 *
 * @author yxuej
 * @since 2026-06-13
 */
@Getter
@Setter
public class ChartData extends SlideConfig {
    private ChartType type;

    private String[] categories;

    private String[] series;

    private Double[][] values;

    public static ChartData of(int index, String title, ChartType chartType) {
        ChartData chartData = new ChartData().build(index, title, SlideConfigType.CHART);
        chartData.setType(chartType);
        return chartData;
    }

    public ChartData setBarData(String[] categoriesData, String[] seriesData, Double[][] valuesData) {
        this.categories = categoriesData;
        this.series = seriesData;
        this.values = valuesData;
        return this;
    }
}
