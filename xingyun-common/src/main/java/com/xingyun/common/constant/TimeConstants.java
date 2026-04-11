/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.constant;

import java.time.format.DateTimeFormatter;

/**
 * 时间常量类
 *
 * @author yxuej
 * @since 2026-04-06
 */
public class TimeConstants {
    /**
     * 年-月-日
     */
    public static final String DATE = "yyyy-MM-dd";

    /**
     * 年-月-日 时:分:秒
     */
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间模式-日期
     */
    public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern(DATE);

    /**
     * 时间模式-日期&时分秒
     */
    public static final DateTimeFormatter FORMATTER_DATE_TIME = DateTimeFormatter.ofPattern(DATE_TIME);
}
