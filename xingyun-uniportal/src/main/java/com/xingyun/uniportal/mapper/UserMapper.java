/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.uniportal.mapper;

import com.xingyun.common.user.UserVo;

/**
 * 用户表数据层
 */
public interface UserMapper {
    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    UserVo getUserByName(String userName);
}
