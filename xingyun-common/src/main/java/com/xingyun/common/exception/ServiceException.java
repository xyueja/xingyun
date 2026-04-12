/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.exception;

/**
 * ServiceException
 *
 * @author yxuej
 * @since 2026-04-12
 */
public class ServiceException extends RuntimeException {
    /**
     * 构造函数
     *
     * @param message 错误信息
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param message 错误信息
     * @param cause   异常信息
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
