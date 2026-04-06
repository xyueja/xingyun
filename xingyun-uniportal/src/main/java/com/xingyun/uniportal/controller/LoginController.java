/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.uniportal.controller;

import com.xingyun.common.base.CommonCode;
import com.xingyun.common.base.resp.BaseResponse;
import com.xingyun.common.cache.RedisUtil;
import com.xingyun.common.constant.CacheConstants;
import com.xingyun.common.constant.HeaderConstants;
import com.xingyun.common.util.StringUtils;
import com.xingyun.uniportal.entity.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/auth")
    public BaseResponse<String> auth(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        // 验证码校验
        String captchaKey = request.getHeader(HeaderConstants.CAPTCHA_ID);
        boolean isCaptchaValid = validateCaptcha(loginRequest.getCode(), captchaKey);
        if (!isCaptchaValid) {
            log.error("[LoginService.auth]: captcha is not valid");
            return BaseResponse.fail(CommonCode.INVALID);
        }
        return BaseResponse.success();
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
