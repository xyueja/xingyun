/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.validate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * 校验规则
 *
 * @author yxuej
 * @since 2026-04-06
 */
@Getter
@Setter
@Slf4j
public class ValidateRule<V> {
    // 参数名
    private String name;

    // 参数值
    private V value;

    // 正则
    private Pattern pattern;

    // 是否允许为空
    private boolean nullable;

    // 断言
    private Predicate<V> predicate;

    /**
     * 构建校验规则
     *
     * @param paramName  参数名
     * @param paramValue 参数值
     * @param <V>        参数类型
     * @return ValidateRule
     */
    public static <V> ValidateRule<V> of(String paramName, V paramValue) {
        ValidateRule<V> rule = new ValidateRule<>();
        rule.setName(paramName);
        rule.setValue(paramValue);
        return rule;
    }

    /**
     * 构建校验规则
     *
     * @param paramName  参数名
     * @param paramValue 参数值
     * @param <V>        参数类型
     * @param regex      正则
     * @return ValidateRule
     */
    public static <V> ValidateRule<V> of(String paramName, V paramValue, Pattern regex) {
        return of(paramName, paramValue, regex, false);
    }

    /**
     * 构建校验规则
     *
     * @param paramName  参数名
     * @param paramValue 参数值
     * @param <V>        参数类型
     * @param regex      正则
     * @param isNullable 是否允许为空
     * @return ValidateRule
     */
    public static <V> ValidateRule<V> of(String paramName, V paramValue, Pattern regex, boolean isNullable) {
        ValidateRule<V> rule = of(paramName, paramValue);
        rule.setPattern(regex);
        rule.setNullable(isNullable);
        return rule;
    }

    /**
     * 构建校验规则
     *
     * @param paramName          参数名
     * @param paramValue         参数值
     * @param <V>                参数类型
     * @param validatorPredicate 校验断言
     * @return ValidateRule
     */
    public static <V> ValidateRule<V> of(String paramName, V paramValue, Predicate<V> validatorPredicate) {
        ValidateRule<V> rule = of(paramName, paramValue);
        rule.setPredicate(validatorPredicate);
        return rule;
    }

    public boolean isValid() {
        boolean isValid = false;
        if (predicate != null) {
            isValid = predicate.test(value);
        }
        if (value instanceof String strValue) {
            if (pattern != null) {
                isValid = ValidateUtils.isValid(strValue, pattern, nullable);
            }
        }

        if (!isValid) {
            log.error("[ValidateRule.isValid]: verify failed, param name is {}", name);
        }
        return isValid;
    }
}
