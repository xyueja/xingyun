package com.xingyun.common.base.resp;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xingyun.common.base.BaseRespCode;
import com.xingyun.common.util.json.JsonUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础响应体
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> {
    // 业务码
    @JsonAlias({"code", "errorCode", "resultCode"})
    private int code;

    // 描述信息
    @JsonAlias({"msg", "message", "errorMsg", "resultMsg"})
    private String msg;

    // 数据
    @JsonAlias({"data", "result"})
    protected T result;

    /**
     * 构建BaseResponse
     *
     * @param respCode 基础响应码
     * @return BaseResponse
     */
    public static <T> BaseResponse<T> of(ResponseCode respCode) {
        return of(respCode.getCode(), respCode.getMsg());
    }

    /**
     * 构建BaseResponse
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     * @return BaseResponse
     */
    public static <T> BaseResponse<T> of(int errorCode, String errorMsg) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(errorCode);
        response.setMsg(errorMsg);
        return response;
    }

    /**
     * 构建成功的响应
     *
     * @param data 数据
     * @return BaseResponse
     */
    public BaseResponse<T> success(T data) {
        BaseResponse<T> response = of(BaseRespCode.SUCCESS);
        response.setResult(data);
        return response;
    }

    /**
     * 响应是否成功
     *
     * @return boolean
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.code == BaseRespCode.SUCCESS.getCode();
    }

    @Override
    public String toString() {
        return JsonUtil.toString(this);
    }
}
