package com.zhuang.music_cms.enums;

/**
 * @Package     : com.zhuang.music_cms.exception
 * @EnumName    : ExceptionType
 * @Description : 自定义异常状体码枚举
 * @Author      : Zhuang
 * @Date        : 2020-04-21 21:30
 */

public enum FileType {

    ///图片
    IMG(1, "img"),
    ///音频
    AUDIO(2, "audio"),
    ///视频
    VIDEO(3, "video"),
    ///App包
    APP(4, "app"),
    ///其他
    OTHER(5, "other");

    private Integer code;
    private String message;

    FileType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
