/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.validate;

import com.xingyun.common.util.StringUtils;

import java.util.regex.Pattern;

/**
 * 校验工具类
 *
 * @author yxuej
 * @since 2026-04-06
 */
public class ValidateUtils {
    /**
     * 参数是否有效
     *
     * @param param   参数
     * @param pattern 正则模式
     * @return boolean
     */
    public static boolean isValid(String param, Pattern pattern) {
        return isValid(param, pattern, false);
    }

    /**
     * 参数是否有效
     *
     * @param param    参数
     * @param pattern  正则模式
     * @param nullable 是否允许为空
     * @return boolean
     */
    public static boolean isValid(String param, Pattern pattern, boolean nullable) {
        if (StringUtils.isEmpty(param)) {
            return nullable;
        }
        return pattern.matcher(param).matches();
    }
}
