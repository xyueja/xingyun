package com.xingyun.common.base.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 基础分页请求实体
 */
@Getter
@Setter
public class BasePageReq {
    private int pageIndex;

    private int pageSize;
}
