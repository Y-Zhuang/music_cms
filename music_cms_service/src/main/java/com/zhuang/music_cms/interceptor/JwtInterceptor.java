package com.zhuang.music_cms.interceptor;

import com.zhuang.music_cms.enums.ExceptionType;
import com.zhuang.music_cms.exception.CustomException;
import com.zhuang.music_cms.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Package     : com.zhuang.music_cms.interceptor
 * @ClassName   : JwtInterceptor 
 * @Description : jwt拦截器
 * @Author      : Zhuang
 * @Date        : 2020-04-21 23:35
 */

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String toKen = request.getHeader("Authorization");
        if (toKen != null && !"".equals(toKen)) {
            Claims claims = jwtUtils.parseJwt(toKen);
            if (claims != null) {
                String api = ((HandlerMethod) handler).getMethodAnnotation(RequestMapping.class).name();
                List<String> apis = (List<String>) claims.get("apis");
                if ("".equals(api)) {
                    return true;
                } else {
                    if (apis.contains(api)) {
                        return true;
                    }else {
                        throw new CustomException(ExceptionType.USER_NO_AUTHORITY, "用户无权限");
                    }
                }
            }
        }
        throw new CustomException(ExceptionType.USER_NO_LOGIN, "请登录");
    }
}
