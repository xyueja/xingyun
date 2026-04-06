/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.uniportal.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * LoginRequest
 *
 * @author yxuej
 * @since 2026-04-05
 */
@Getter
@Setter
public class LoginRequest {
    // 用户名
    private String username;

    // 密码
    private String password;

    // 验证码
    private String code;
}
