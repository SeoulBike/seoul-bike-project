package com.study5.seoul.bike.dto.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SingleResult<T> extends CommonResult {
    private T data;
}
