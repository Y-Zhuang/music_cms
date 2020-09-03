package com.zhuang.music_cms.exception;

import com.zhuang.music_cms.enums.ExceptionType;
import lombok.Data;

/**
 * @Package     : com.zhuang.music_cms.exception
 * @ClassName   : AjaxResponse 
 * @Description : 返回给前端的数据
 * @Author      : Zhuang
 * @Date        : 2020-04-21 21:51
 */

@Data
public class JsonResponse {

    private boolean isok;
    private int code;
    private String message;
    private Object data;

    private JsonResponse() {

    }

    public static JsonResponse error(CustomException e) {

        JsonResponse resultBean = new JsonResponse();
        resultBean.setIsok(false);
        resultBean.setCode(e.getCode());
        if (e.getCode() == ExceptionType.USER_INPUT_ERROR.getCode()) {
            resultBean.setMessage(e.getMessage());
        } else if (e.getCode() == ExceptionType.SYSTEM_ERROR.getCode()) {
            resultBean.setMessage(e.getMessage());
        } else if (e.getCode() == ExceptionType.USER_NO_LOGIN.getCode()) {
            resultBean.setMessage(e.getMessage());
        } else if (e.getCode() == ExceptionType.USER_NO_AUTHORITY.getCode()) {
            resultBean.setMessage(e.getMessage());
        }else if (e.getCode() == ExceptionType.LOGIN_TIMEOUT.getCode()) {
            resultBean.setMessage(e.getMessage());
        } else {
            resultBean.setMessage("未知异常");
        }
        return resultBean;
    }

    public static JsonResponse success() {
        JsonResponse resultBean = new JsonResponse();
        resultBean.setIsok(true);
        resultBean.setCode(200);
        resultBean.setMessage("success");
        return resultBean;
    }

    public static JsonResponse success(Object data) {
        JsonResponse resultBean = new JsonResponse();
        resultBean.setIsok(true);
        resultBean.setCode(200);
        resultBean.setMessage("success");
        resultBean.setData(data);
        return resultBean;
    }

}
