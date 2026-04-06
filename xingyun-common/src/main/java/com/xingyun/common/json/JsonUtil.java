/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.json;

import com.alibaba.fastjson2.JSONObject;

import java.util.List;
import java.util.Map;


/**
 * json工具类
 */
public class JsonUtil {
    /**
     * 对象转json字符串
     *
     * @param object json对象
     * @return json字符串
     */
    public static String toString(Object object) {
        return JSONObject.toJSONString(object);
    }

    /**
     * json字符转List
     *
     * @param json json串
     * @return json字符串
     */
    public static List<Object> toList(String json) {
        return toObject(json, Type.LIST_OBJECT);
    }

    /**
     * json字符串转Map
     *
     * @param json json串
     * @return json字符串
     */
    public static Map<String, Object> toMap(String json) {
        return toObject(json, Type.MAP_OBJECT);
    }

    /**
     * 字符串转json对象
     *
     * @param json  json字符串
     * @param clazz 对象Class
     * @param <T>   对象类型
     * @return json对象
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        return JSONObject.parseObject(json, clazz);
    }

    /**
     * 字符串转json对象
     *
     * @param json json字符串
     * @param type 对象Class
     * @param <T>  对象类型
     * @return json对象
     */
    public static <T> T toObject(String json, Type<T> type) {
        return JSONObject.parseObject(json, type);
    }
}
