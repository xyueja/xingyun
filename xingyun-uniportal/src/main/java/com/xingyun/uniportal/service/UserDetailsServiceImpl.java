/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.uniportal.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义用户详情服务实现类，实现Spring Security框架的UserDetailsService <br>
 * 通过Spring Security框架对用户进行认证
 *
 * @author yxuej
 * @since 2026-04-11
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
