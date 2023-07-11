package com.study5.seoul.bike.service.impl;

import com.study5.seoul.bike.dto.common.CommonResult;
import com.study5.seoul.bike.dto.common.ListResult;
import com.study5.seoul.bike.dto.common.SingleResult;
import com.study5.seoul.bike.service.ResponseService;
import com.study5.seoul.bike.type.code.ErrorCode;
import com.study5.seoul.bike.type.code.SuccessCode;

import java.util.List;

import static com.study5.seoul.bike.type.code.SuccessCode.SUCCESS;

public class ResponseServiceImpl implements ResponseService {

    @Override
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = SingleResult.<T>builder()
                .data(data)
                .build();

        result.update(true, SUCCESS.getCode(), SUCCESS.getMessage());
        return result;
    }

    @Override
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = ListResult.<T>builder()
                .data(list)
                .build();

        result.update(true, SUCCESS.getCode(), SUCCESS.getMessage());
        return result;
    }

    @Override
    public CommonResult getSuccessResult(SuccessCode successCode) {
        return new CommonResult(true, successCode.getCode(), successCode.getMessage());
    }

    @Override
    public CommonResult getFailResult(ErrorCode errorCode) {
        return new CommonResult(true, errorCode.getCode(), errorCode.getMessage());
    }

}
