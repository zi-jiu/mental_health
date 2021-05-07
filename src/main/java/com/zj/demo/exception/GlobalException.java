package com.zj.demo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName GlobalException
 * @Author 字九
 * @Date 2021/3/23 14:31
 * @Description
 **/
@ControllerAdvice
public class GlobalException {
    @ResponseBody
    @ExceptionHandler(MyException.class)
    public Map<String, Object> handleCustomException(MyException customException) {
        Map<String, Object> errorResultMap = new HashMap<>(16);
        errorResultMap.put("code", customException.getCode());
        errorResultMap.put("message", customException.getMessage());
        return errorResultMap;
    }
}
