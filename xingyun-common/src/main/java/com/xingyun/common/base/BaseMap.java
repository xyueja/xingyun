package com.xingyun.common.base;

import com.xingyun.common.json.JsonUtil;

import java.util.HashMap;

/**
 * 基础Map，继承HashMap进行扩展，方便操作
 */
public class BaseMap extends HashMap<String, Object> {
    /**
     * 构建一个空的BaseMap
     *
     * @return BaseMap
     */
    public static BaseMap empty() {
        return new BaseMap();
    }

    /**
     * 构建简单的BaseMap
     *
     * @param key   键
     * @param value 值
     * @return BaseMap
     */
    public static BaseMap of(String key, Object value) {
        return empty().add(key, value);
    }

    /**
     * 构建简单的BaseMap
     *
     * @param key1   键1
     * @param value1 值1
     * @param key2   键2
     * @param value2 值2
     * @return BaseMap
     */
    public static BaseMap of(String key1, Object value1, String key2, Object value2) {
        return of(key1, value1).add(key2, value2);
    }

    /**
     * BaseMap添加元素
     *
     * @param key   键
     * @param value 值
     * @return BaseMap
     */
    public BaseMap add(String key, Object value) {
        this.put(key, value);
        return this;
    }

    /**
     * BaseMap转json字符串
     *
     * @return string
     */
    @Override
    public String toString() {
        return JsonUtil.toString(this);
    }
}
