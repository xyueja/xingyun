package com.xingyun.common.base;

import com.xingyun.common.base.resp.BaseRespCode;

/**
 * 基础响应码
 */
public enum CommonRespCode implements BaseRespCode {
    /**
     * 成功
     */
    SUCCESS(0, "success"),

    /**
     * 失败
     */
    FAIL(1, "fail"),

    /**
     * 访问被拒绝
     */
    ACCESS_DENIED(403, "Access Denied"),

    /**
     * 系统内部错误
     */
    SYSTEM_INNER_ERROR(500, "System Inner Error"),

    /**
     * 系统未知错误
     */
    SYSTEM_UNKNOW_ERROR(500, "System Inner Error");

    private final int code;

    private final String msg;

    /**
     * 构造函数
     *
     * @param code 编码
     * @param msg  编码信息
     */
    CommonRespCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
