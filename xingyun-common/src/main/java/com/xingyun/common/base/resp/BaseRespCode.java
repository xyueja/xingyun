package com.xingyun.common.base.resp;

/**
 * 基础响应码
 */
public interface BaseRespCode {
    /**
     * 获取错误码
     *
     * @return code
     */
    int getCode();

    /**
     * 获取错误信息
     *
     * @return msg
     */
    String getMsg();
}
