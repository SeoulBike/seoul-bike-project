package com.study5.seoul.bike.service;

import com.study5.seoul.bike.dto.common.CommonResult;
import com.study5.seoul.bike.dto.common.ListResult;
import com.study5.seoul.bike.dto.common.SingleResult;
import com.study5.seoul.bike.type.code.ErrorCode;
import com.study5.seoul.bike.type.code.SuccessCode;

import java.util.List;

public interface ResponseService {

    <T> SingleResult<T> getSingleResult(T data);

    <T> ListResult<T> getListResult(List<T> list);

    CommonResult getSuccessResult(SuccessCode successCode);

    CommonResult getFailResult(ErrorCode errorCode);

}
