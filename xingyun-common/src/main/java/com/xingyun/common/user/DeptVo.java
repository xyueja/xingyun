/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.user;

import com.xingyun.common.base.entity.BaseEntity;
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
public class DeptVo extends BaseEntity {
    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 部门路径
     */
    private String deptPath;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private int orderNum;

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
     * 父部门名称
     */
    private String parentName;

    /**
     * 子部门
     */
    private List<DeptVo> children = new ArrayList<>();
}
