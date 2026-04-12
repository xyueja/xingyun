/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.uniportal.service.impl;

import com.xingyun.common.user.UserVo;
import com.xingyun.uniportal.mapper.UserMapper;
import com.xingyun.uniportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author yxuej
 * @since 2026-04-11
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVo getUserByName(String username) {
        return userMapper.getUserByName(username);
    }
}
