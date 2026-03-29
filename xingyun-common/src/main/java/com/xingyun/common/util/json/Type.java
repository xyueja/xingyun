/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.util.json;

import com.alibaba.fastjson2.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * json类型
 */
public class Type<T> extends TypeReference<T> {
    /**
     * List-String
     */
    public static Type<List<String>> LIST_STRING = new Type<>() {
    };

    /**
     * List-Object
     */
    public static Type<List<Object>> LIST_OBJECT = new Type<>() {
    };

    /**
     * List-MapObject
     */
    public static Type<List<Map<String, Object>>> LIST_MAP_OBJECT = new Type<>() {
    };

    /**
     * List-MapString
     */
    public static Type<List<Map<String, String>>> LIST_MAP_STRING = new Type<>() {
    };

    /**
     * Map-String
     */
    public static Type<Map<String, String>> MAP_STRING = new Type<>() {
    };

    /**
     * Map-Object
     */
    public static Type<Map<String, Object>> MAP_OBJECT = new Type<>() {
    };
}
