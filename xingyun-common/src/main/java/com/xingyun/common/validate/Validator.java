/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.validate;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数校验器
 *
 * @author yxuej
 * @since 2026-04-06
 */
@Getter
@Setter
public class Validator {
    // 校验规则集
    private List<ValidateRule<Object>> rules = new ArrayList<>();

    /**
     * 添加校验规则
     *
     * @param rule 规则
     * @return Validator
     */
    public Validator add(ValidateRule<Object> rule) {
        rules.add(rule);
        return this;
    }

    /**
     * 校验
     *
     * @return boolean
     */
    public boolean verify() {
        return rules.stream().allMatch(ValidateRule::isValid);
    }
}
