package com.xingyun.common.base.resp;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页响应数据
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageVo {
    // 总数量
    @JsonAlias({"total", "counts", "totalCounts"})
    private int totalCounts;

    // 总页数
    private int totalPages;

    // 分页偏移量
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int pageIndex;

    // 每页数量
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int pageSize;
}
