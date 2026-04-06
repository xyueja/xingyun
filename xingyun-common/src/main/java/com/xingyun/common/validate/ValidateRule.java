/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.validate;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Predicate;

/**
 * 校验规则
 *
 * @author yxuej
 * @since 2026-04-06
 */
@Getter
@Setter
public class ValidateRule<V> {
    // 参数名
    private String name;

    // 参数值
    private V value;

    private Predicate<V> predicate;

    /**
     * 构建校验规则
     *
     * @param param          参数
     * @param paramValue     参数值
     * @param paramPredicate 参数断言
     * @param <V>            参数类型
     * @return ValidateRule
     */
    public static <V> ValidateRule<V> of(String param, V paramValue, Predicate<V> paramPredicate) {
        ValidateRule<V> rule = new ValidateRule<>();
        rule.setName(param);
        rule.setValue(paramValue);
        rule.setPredicate(paramPredicate);
        return rule;
    }
}
