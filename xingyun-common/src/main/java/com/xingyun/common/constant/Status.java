/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.constant;

import lombok.Getter;

/**
 * 全局通用转态枚举
 *
 * @author yxuej
 * @since 2026-04-12
 */
@Getter
public enum Status {
    /**
     * 正常
     */
    OK(0, "正常"),

    /**
     * 不可用
     */
    DISABLE(1, "停用"),

    /**
     * 已删除
     */
    DELETED(2, "删除");

    private final int code;

    private final String desc;

    /**
     * 构造函数
     *
     * @param code 状态编码
     * @param desc 状态描述
     */
    Status(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
