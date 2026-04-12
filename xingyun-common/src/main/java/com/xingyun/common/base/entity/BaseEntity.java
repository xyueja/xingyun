/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.base.entity;

import lombok.Data;

/**
 * 基础试图
 *
 * @author yxuej
 * @since 2026-04-12
 */
@Data
public class BaseEntity {
    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 账号状态（0正常 1停用 2删除）
     */
    private int status;
}
