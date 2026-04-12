/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.uniportal.service;

import com.xingyun.common.user.UserVo;

/**
 * UserService
 *
 * @author yxuej
 * @since 2026-04-11
 */
public interface UserService {
    /**
     * 获取系统用户信息
     *
     * @param username 用户名
     * @return SysUserVo
     */
    UserVo getUserByName(String username);
}
