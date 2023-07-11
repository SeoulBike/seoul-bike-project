package com.study5.seoul.bike.dto.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ListResult<T> extends CommonResult {
    private List<T> data;
}
