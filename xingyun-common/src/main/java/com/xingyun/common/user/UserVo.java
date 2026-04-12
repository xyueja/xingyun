/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.user;

import com.xingyun.common.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 系统用户
 *
 * @author yxuej
 * @since 2026-04-11
 */
@Getter
@Setter
public class UserVo extends BaseEntity {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private String loginTime;

    /**
     * 密码最后更新时间
     */
    private String pwdUpdateTime;

    /**
     * 部门对象
     */
    private DeptVo dept;

    /**
     * 角色对象
     */
    private List<RoleVo> roles;

    /**
     * 角色组
     */
    private Long[] roleIds;

    /**
     * 岗位组
     */
    private Long[] postIds;

    /**
     * 角色ID
     */
    private Long roleId;
}
