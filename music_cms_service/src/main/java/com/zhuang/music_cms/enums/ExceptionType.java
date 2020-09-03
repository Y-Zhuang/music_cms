package com.zhuang.music_cms.enums;

/**
 * @Package     : com.zhuang.music_cms.exception
 * @EnumName    : ExceptionType
 * @Description : 自定义异常状体码枚举
 * @Author      : Zhuang
 * @Date        : 2020-04-21 21:30
 */

public enum ExceptionType {

    /// 用户输入异常
    USER_INPUT_ERROR(400, "用户输入异常"),
    /// 未登录
    USER_NO_LOGIN( 401, "用户未登录"),
    /// 登录超时
    LOGIN_TIMEOUT(440, "登录超时"),
    /// 用户无权限
    USER_NO_AUTHORITY(403,"用户无权限"),
    /// 系统服务异常
    SYSTEM_ERROR(500, "系统服务异常"),
    /// 其他未知异常
    OTHER_ERROR(999, "其他未知异常");

    ExceptionType() {

    }

    ExceptionType(int code, String typeDesc) {
        this.code = code;
        this.typeDesc = typeDesc;
    }

    private String typeDesc;

    private int code;

    public String getTypeDesc() {
        return typeDesc;
    }

    public int getCode() {
        return code;
    }

}
