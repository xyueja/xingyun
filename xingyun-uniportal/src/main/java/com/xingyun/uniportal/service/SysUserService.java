/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.uniportal.service;

import com.xingyun.common.user.SysUserVo;

/**
 * UserService
 *
 * @author yxuej
 * @since 2026-04-11
 */
public interface SysUserService {
    /**
     * 获取系统用户信息
     *
     * @param username 用户名
     * @return SysUserVo
     */
    SysUserVo getUserByName(String username);
}
