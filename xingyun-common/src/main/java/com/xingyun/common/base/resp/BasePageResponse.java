package com.xingyun.common.base.resp;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasePageResponse<T> extends BaseResponse<List<T>> {
    @JsonAlias({"pageVo", "pageVO"})
    private PageVo pageVo;

    @JsonAlias({"data", "result", "results"})
    private List<T> result;
}
