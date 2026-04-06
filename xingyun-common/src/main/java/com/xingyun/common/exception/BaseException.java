/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.exception;

/**
 * 基础服务异常
 *
 * @author yxuej
 * @since 2026-04-05
 */
public class BaseException extends RuntimeException {
    /**
     * 构造函数
     *
     * @param message 错误信息
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param message 错误信息
     * @param cause   异常信息
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
