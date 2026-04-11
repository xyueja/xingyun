/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户部门信息
 *
 * @author yxuej
 * @since 2026-04-11
 */
@Data
public class SysDeptVo {
    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态:0正常,1停用
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 父部门名称
     */
    private String parentName;

    /**
     * 子部门
     */
    private List<SysDeptVo> children = new ArrayList<>();
}
