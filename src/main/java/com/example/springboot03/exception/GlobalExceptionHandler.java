package com.example.springboot03.exception;

import com.example.springboot03.common.BaseResponse;
import com.example.springboot03.common.ErrorCode;
import com.example.springboot03.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e){
      log.error("businessException:"+e.getMessage(),e);
      return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());

    }
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(BusinessException e){
        log.error("runtimeException",e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }

}
