package com.xcw.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException{
    @Getter
    @Setter
    private Integer code;

    @Getter
    @Setter
    private String message;
}
