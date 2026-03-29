package com.xingyun.common.base.resp;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseCountResponse<T> extends BaseResponse<List<T>> {
    private int total;

    @JsonAlias({"data", "result", "results"})
    private List<T> result;
}
