package com.hxl.payx.exception.handler;

import com.hxl.payx.constants.ResponseEnum;
import com.hxl.payx.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Description: 运行时错误捕获
 * @Author: hanxuanliang
 * @Date: 2020/1/29 16:19
 */
@RestControllerAdvice
@Slf4j
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseVo<String> runtimeHandle(RuntimeException exception) {
        return ResponseVo.error(ResponseEnum.INTERNAL_ERROR, exception.getMessage());
    }

    /**
     * 表单提交参数错误，拦截异常
     * @param exception 表单提交参数错误
     * @return ResponseVo<String> 统一错误error，包含exception中的BindingResult，然后再拼接错误
     * @date: 2020/1/30 18:20
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVo<String> argumentExceptionHandler(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        log.error("提交的参数有误：{}", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        return ResponseVo.error(ResponseEnum.PARAM_ERROR, result);
    }
}
