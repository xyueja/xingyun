/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.constant;

import java.util.regex.Pattern;

/**
 * 正则表达式常量类
 *
 * @author yxuej
 * @since 2026-04-06
 */
public class RegexConstants {
    /**
     * 常规字符正则表达式
     */
    public static final Pattern NORMAL = Pattern.compile("^[a-zA-Z0-9_-]{1,50}$");

    /**
     * 邮箱正则表达式
     */
    private static final Pattern EMAIL = Pattern.compile("^[a-zA-Z0-9._%+-]{1,30}@[a-zA-Z0-9.-]{1,20}\\.[a-zA-Z]{2,8}$");
}
