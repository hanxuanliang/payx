package com.hxl.payx.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hxl.payx.constants.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * @Description: 返回响应对象
 * @Author: hanxuanliang
 * @Date: 2020/1/29 11:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)  // 字段为空在序列化阶段会被忽略
public class ResponseVo<T> {

    private Integer code;

    private String msg;

    private T data;

    public ResponseVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResponseVo<T> success() {
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getDesc());
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum) {
        return new ResponseVo<T>(responseEnum.getCode(), responseEnum.getDesc());
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum, String msg) {
        return new ResponseVo<T>(responseEnum.getCode(), msg);
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum, BindingResult result) {
        return new ResponseVo<T>(responseEnum.getCode(), Objects.requireNonNull(result.getFieldError()).getField() + " " + result.getFieldError().getDefaultMessage());
    }
}
