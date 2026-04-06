/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.exception;

import com.xingyun.common.base.CommonRespCode;
import com.xingyun.common.base.resp.BaseResponse;
import com.xingyun.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * 全局异常处理器
 *
 * @author yxuej
 * @since 2026-04-05
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 权限校验异常
     *
     * @param request 请求局部
     * @param e       异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public BaseResponse<String> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        String requestUri = request.getRequestURI();
        log.error("[GlobalExceptionHandler.accessDenied]: uri={}", requestUri, e);
        return BaseResponse.fail(CommonRespCode.ACCESS_DENIED);
    }

    /**
     * 请求方式不支持
     *
     * @param request 请求局部
     * @param e       异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse<String> handleHttpRequestMethodNotSupported(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        String requestUri = request.getRequestURI();
        log.error("[GlobalExceptionHandler.methodNotSupported]: uri={}, method={}", requestUri, e.getMethod());
        return BaseResponse.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), "Method not allowed");
    }

    /**
     * 请求路径中缺少必需的路径变量
     *
     * @param request 请求局部
     * @param e       异常
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public BaseResponse<String> handleMissingPathVariableException(HttpServletRequest request, MissingPathVariableException e) {
        String requestUri = request.getRequestURI();
        log.error("[GlobalExceptionHandler.missingPathVariable]: uri={}", requestUri, e);
        return BaseResponse.fail(HttpStatus.BAD_REQUEST.value(), String.format(Locale.ENGLISH, "Missing Path Variable:[%s]", e.getVariableName()));
    }

    /**
     * 请求参数类型不匹配
     *
     * @param request 请求局部
     * @param e       异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponse<String> handleMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
        String requestUri = request.getRequestURI();
        String value = Objects.toString(e.getValue(), StringUtils.EMPTY);
        String actuallyType = Optional.ofNullable(e.getRequiredType()).map(Class::getName).orElse(null);
        log.error("[GlobalExceptionHandler.argumentTypeMismatch]: uri={}", requestUri, e);
        return BaseResponse.fail(HttpStatus.BAD_REQUEST.value(), String.format(Locale.ENGLISH, "Argument Type Mismatch，param [%s] need type：'%s'，actually was：'%s'", e.getName(), actuallyType, value));
    }

    /**
     * 系统异常
     *
     * @param request 请求局部
     * @param e       异常
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<String> handleException(HttpServletRequest request, Exception e) {
        String requestUri = request.getRequestURI();
        log.error("[GlobalExceptionHandler.exception]: uri={}", requestUri, e);
        return BaseResponse.fail(CommonRespCode.SYSTEM_INNER_ERROR);
    }

    /**
     * 拦截未知的运行时异常
     *
     * @param request 请求局部
     * @param e       异常
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<String> handleRuntimeException(HttpServletRequest request, RuntimeException e) {
        String requestUri = request.getRequestURI();
        log.error("[GlobalExceptionHandler.runtimeException]: uri={}", requestUri, e);
        return BaseResponse.fail(CommonRespCode.SYSTEM_UNKNOW_ERROR);
    }
}
