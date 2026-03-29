package com.xingyun.common.base;

import com.xingyun.common.base.resp.ResponseCode;

/**
 * 基础响应码
 */
public enum BaseRespCode implements ResponseCode {
    /**
     * 成功
     */
    SUCCESS(0, "success"),

    /**
     * 失败
     */
    FAIL(1, "fail");

    private final int code;

    private final String msg;

    /**
     * 构造函数
     *
     * @param code 编码
     * @param msg 编码信息
     */
    BaseRespCode(int code, String msg) {
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
