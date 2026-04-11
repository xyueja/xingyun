/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.uniportal.mapper;

import com.xingyun.common.user.SysUserVo;

/**
 * 用户表数据层
 */
public interface SysUserMapper {
    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUserVo getUserByName(String userName);
}
