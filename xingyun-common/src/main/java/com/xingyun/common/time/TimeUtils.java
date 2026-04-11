/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.time;

import com.xingyun.common.constant.TimeConstants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * TimeUtil
 *
 * @author yxuej
 * @since 2026-04-06
 */
public class TimeUtils {
    /**
     * 时间转换
     *
     * @param time 时间戳
     * @return String
     */
    public static String format(long time) {
        return format(time, TimeConstants.FORMATTER_DATE_TIME);
    }

    /**
     * 时间转换
     *
     * @param time    时间戳
     * @param pattern 时间模式
     * @return String
     */
    public static String format(long time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return format(time, formatter);
    }

    /**
     * 时间转换
     *
     * @param time      时间戳
     * @param formatter 时间模式
     * @return String
     */
    public static String format(long time, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return localDateTime.format(formatter);
    }

    public static void main(String[] args) {
    }
}
