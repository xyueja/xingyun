package com.xingyun.common.base.resp;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xingyun.common.base.CommonCode;
import com.xingyun.common.json.JsonUtil;
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
    public static <T> BaseResponse<T> of(BaseCode respCode) {
        return of(respCode.getCode(), respCode.getMsg());
    }

    /**
     * 构建BaseResponse
     *
     * @param respCode 基础响应码
     * @param errorMsg 错误信息
     * @return BaseResponse
     */
    public static <T> BaseResponse<T> of(BaseCode respCode, String errorMsg) {
        return of(respCode.getCode(), errorMsg);
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
     * 构建成功的响应，响应体不需要返回数据，有状态码就行
     *
     * @return BaseResponse
     */
    public static <T> BaseResponse<T> success() {
        return of(CommonCode.SUCCESS);
    }

    /**
     * 构建成功的响应
     *
     * @param data 数据
     * @return BaseResponse
     */
    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = of(CommonCode.SUCCESS);
        response.setResult(data);
        return response;
    }

    /**
     * 构建失败的响应
     *
     * @param errorMsg 错误信息
     * @return BaseResponse 基础响应
     */
    public static BaseResponse<String> fail(String errorMsg) {
        return BaseResponse.of(CommonCode.FAIL, errorMsg);
    }

    /**
     * 构建失败的响应
     *
     * @param respCode 基础响应码
     * @return BaseResponse 基础响应
     */
    public static BaseResponse<String> fail(BaseCode respCode) {
        return BaseResponse.of(respCode.getCode(), respCode.getMsg());
    }

    /**
     * 构建失败的响应
     *
     * @param respCode 基础响应码
     * @param errorMsg 错误信息
     * @return BaseResponse 基础响应
     */
    public static BaseResponse<String> fail(BaseCode respCode, String errorMsg) {
        return BaseResponse.of(respCode.getCode(), errorMsg);
    }

    /**
     * 构建失败的响应
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     * @return BaseResponse 基础响应
     */
    public static BaseResponse<String> fail(int errorCode, String errorMsg) {
        return BaseResponse.of(errorCode, errorMsg);
    }

    /**
     * 判断响应是否成功，加上JsonIgnore注解避免被序列化返回给前端
     *
     * @return boolean
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.code == CommonCode.SUCCESS.getCode();
    }

    @Override
    public String toString() {
        // 重写toString方法，返回Json格式数据
        return JsonUtil.toString(this);
    }
}
