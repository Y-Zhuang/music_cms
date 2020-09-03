package com.zhuang.music_cms.exception;

import com.zhuang.music_cms.enums.ExceptionType;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Package     : com.zhuang.music_cms.exception
 * @ClassName   : WebExceptionHandler 
 * @Description : 异常处理器
 * @Author      : Zhuang
 * @Date        : 2020-04-21 22:25
 */

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public JsonResponse customerException(CustomException e) {
        return JsonResponse.error(e);
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public JsonResponse bindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return JsonResponse.error(new CustomException(ExceptionType.USER_INPUT_ERROR, message));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public JsonResponse expiredJwtException(ExpiredJwtException e){
        return JsonResponse.error(new CustomException(ExceptionType.LOGIN_TIMEOUT, "token已过期"));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResponse exception(Exception e) {
        e.printStackTrace();
        return JsonResponse.error(new CustomException(ExceptionType.OTHER_ERROR, "未知异常"));
    }

}
