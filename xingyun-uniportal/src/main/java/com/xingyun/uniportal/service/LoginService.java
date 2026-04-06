/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.uniportal.service;

import com.xingyun.uniportal.entity.LoginRequest;
import org.springframework.stereotype.Component;

/**
 * 登陆服务
 *
 * @author yxuej
 * @since 2026-04-05
 */
@Component
public class LoginService {
    public String auth(LoginRequest loginRequest, String captchaKey) {
        String username = loginRequest.getUsername();

        String password = loginRequest.getPassword();


        // 登录前置校验
        loginPreCheck(username, password);
        return "";
    }

    private boolean loginPreCheck(String username, String password) {
        return false;
    }
}
