package com.xcw.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class R implements Serializable {
    private Boolean isSuccess;
    private String message;
    private Integer code;
    private Map<String, Object> data;

    public R(){}

    public R(Boolean isSuccess, String message, Integer code) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.code = code;
        this.data = new HashMap<>();

    }

    public static R ok(){
        return new R(true, "操作成功", ResultCode.SUCCESS);
    }

    public static R error(){
        return new R(false, "操作失败", ResultCode.FAILURE);
    }

    public R isSuccess(Boolean isSuccess){
        this.isSuccess = isSuccess;
        return this;
    }

    public R message(String message){
        this.message = message;
        return this;
    }

    public R code(Integer code){
        this.code = code;
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return  this;
    }

    public R data(Map<String, Object> map){
        this.data = map;
        return this;
    }
}
