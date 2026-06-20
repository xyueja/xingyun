/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.ppt.ppt.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * TableData
 *
 * @author yxuej
 * @since 2026-06-20
 */
@Getter
@Setter
public class TableData extends SlideConfig {
    private List<String> headers;

    private List<List<String>> rows;

    // 默认从第二行开始解析
    private int startRowIndex = 2;
}
