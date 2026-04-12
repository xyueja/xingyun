/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 登陆用户对象
 *
 * @author yxuej
 * @since 2026-04-11
 */
@Data
public class LoginUserVo implements UserDetails {
    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 用户信息
     */
    private UserVo user;

    /**
     * 构造函数
     */
    public LoginUserVo() {
    }

    /**
     * 构造函数
     *
     * @param user        系统用户信息
     * @param permissions 权限
     */
    public LoginUserVo(UserVo user, Set<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
