/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.uniportal.service.impl;

import com.xingyun.common.user.SysUserVo;
import com.xingyun.uniportal.mapper.SysUserMapper;
import com.xingyun.uniportal.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author yxuej
 * @since 2026-04-11
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public SysUserVo getUserByName(String username) {
        return userMapper.getUserByName(username);
    }
}
