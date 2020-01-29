package com.hxl.payx.exception.handler;

import com.hxl.payx.constants.ResponseEnum;
import com.hxl.payx.vo.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Description: 运行时错误捕获
 * @Author: hanxuanliang
 * @Date: 2020/1/29 16:19
 */
@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseVo<String> runtimeHandle(RuntimeException exception) {
        return ResponseVo.error(ResponseEnum.INTERNAL_ERROR, exception.getMessage());
    }
}
