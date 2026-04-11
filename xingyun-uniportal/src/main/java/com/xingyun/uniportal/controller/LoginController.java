/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.uniportal.controller;

import com.xingyun.common.base.CommonCode;
import com.xingyun.common.base.resp.BaseResponse;
import com.xingyun.common.cache.RedisUtil;
import com.xingyun.common.constant.CacheConstants;
import com.xingyun.common.constant.HeaderConstants;
import com.xingyun.common.user.LoginUserVo;
import com.xingyun.common.util.StringUtils;
import com.xingyun.common.validate.ValidateRule;
import com.xingyun.common.validate.Validator;
import com.xingyun.uniportal.entity.LoginRequest;
import com.xingyun.uniportal.security.AuthenticationContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

/**
 * LoginController
 *
 * @author yxuej
 * @since 2026-04-05
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    private static final Pattern PATTERN_USERNAME = Pattern.compile("^[a-zA-Z0-9_]{5,20}$");

    private static final Pattern PATTERN_PASSWORD = Pattern.compile("^[a-zA-Z0-9!@#$%_]{6,20}$");

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public BaseResponse<LoginUserVo> auth(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        // 验证码校验
        String captchaKey = request.getHeader(HeaderConstants.CAPTCHA_ID);
        boolean isCaptchaValid = validateCaptcha(loginRequest.getCode(), captchaKey);
        if (!isCaptchaValid) {
            log.error("[LoginController.auth]: captcha is not valid");
            return BaseResponse.fail(CommonCode.INVALID);
        }

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        boolean isValid = new Validator().add(ValidateRule.of("username", username,
                PATTERN_USERNAME)).add(ValidateRule.of("password", password, PATTERN_PASSWORD)).verify();
        if (!isValid) {
            log.error("[LoginController.auth]: Parameter is invalid");
            return BaseResponse.fail(CommonCode.ILLEGAL);
        }

        Authentication authentication = null;
        try {

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            log.error("[LoginController.auth]: Authentication exception", e);
        } finally {
            AuthenticationContextHolder.clearContext();
        }

        LoginUserVo loginUserVo = (LoginUserVo) authentication.getPrincipal();
        return BaseResponse.success(loginUserVo);
    }

    private boolean validateCaptcha(String code, String captchaKey) {
        String redisKey = CacheConstants.CAPTCHA_KEY_PREFIX + captchaKey;
        String captcha = RedisUtil.get(redisKey);
        if (StringUtils.isBlank(captcha)) {
            return false;
        }

        RedisUtil.delete(redisKey);
        return captcha.equalsIgnoreCase(code);
    }
}
