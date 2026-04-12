/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.uniportal.service;

import com.xingyun.common.exception.ServiceException;
import com.xingyun.common.user.LoginUserVo;
import com.xingyun.common.user.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 自定义用户详情服务实现类，实现Spring Security框架的UserDetailsService <br>
 * 通过Spring Security框架对用户进行认证
 *
 * @author yxuej
 * @since 2026-04-11
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo userVo = userService.getUserByName(username);
        if (Objects.isNull(userVo)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("[UserDetailsService.loadUserByUsername]: user not found");
        }
        return new LoginUserVo(userVo, null);
    }
}
