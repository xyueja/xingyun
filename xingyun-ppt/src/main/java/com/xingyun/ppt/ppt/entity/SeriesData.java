package com.xingyun.ppt.ppt.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * SeriesData
 *
 * @author yxuej
 * @since 2026-06-14
 */
@Getter
@Setter
@Builder
public class SeriesData {
    // 系列
    private List<String> series;

    // 类别
    private List<String> categories;

    // 数据（每一个列表都有一个对应类别的列表数据）
    private List<List<Double>> values;
}
