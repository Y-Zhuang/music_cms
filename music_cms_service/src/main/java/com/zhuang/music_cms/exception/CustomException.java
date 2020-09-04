package com.zhuang.music_cms.exception;

import com.zhuang.music_cms.enums.ExceptionType;

/**
 * @Package     : com.zhuang.music_cms.exception
 * @ClassName   : CustomException
 * @Description : 自定义异常
 * @Author      : Zhuang
 * @Date        : 2020-04-21 21:45
 */

public class CustomException extends RuntimeException {

    private int code ;
    private String message;

    public CustomException(ExceptionType exceptionTypeEnum, String message) {
        this.code = exceptionTypeEnum.getCode();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
