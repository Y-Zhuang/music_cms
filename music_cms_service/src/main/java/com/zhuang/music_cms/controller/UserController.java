package com.zhuang.music_cms.controller;

import com.zhuang.music_cms.exception.CustomException;
import com.zhuang.music_cms.enums.ExceptionType;
import com.zhuang.music_cms.exception.JsonResponse;
import com.zhuang.music_cms.model.vo.RequestUser;
import com.zhuang.music_cms.service.UserService;
import com.zhuang.music_cms.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package     : com.zhuang.music_cms.controller
 * @ClassName   : UserController 
 * @Description : 用户控制器
 * @Author      : Zhuang
 * @Date        : 2020-04-21 20:37
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping(value = "/users", name = "API_USER_GET")
    public JsonResponse getUserAll(int pageCode, int pageSize, String search) {
        return JsonResponse.success(userService.getUserAll(pageCode, pageSize, search));
    }

    @GetMapping("/getUserByToKen")
    public JsonResponse getUserByToKen(HttpServletRequest request) {
        String toKen = request.getHeader("Authorization");
        Claims claims = jwtUtils.parseJwt(toKen);
        if (claims != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", claims.getId());
            map.put("userName", claims.getSubject());
            return JsonResponse.success(map);
        }
        return JsonResponse.error(new CustomException(ExceptionType.USER_NO_LOGIN, "请登录"));
    }

    @PostMapping("/login")
    public JsonResponse login(String phoneNumber, String password) {
        String token = userService.login(phoneNumber, password);
        if (token != null) {
            return JsonResponse.success(token);
        }
        return JsonResponse.error(new CustomException(ExceptionType.USER_NO_LOGIN, "用户名或密码有误"));
    }

    @PostMapping(value = "/user", name = "API_USER_POST")
    public JsonResponse addUser(@Valid RequestUser requestUser) throws Exception {
        userService.addUser(requestUser);
        return JsonResponse.success("添加成功");
    }

    @DeleteMapping(value = "/user/{id}", name = "API_USER_DELETE")
    public JsonResponse deleteUser(@PathVariable Integer id) throws Exception {
        userService.deleteUser(id);
        return JsonResponse.success("删除成功");

    }

    @GetMapping(value = "/user/{id}", name = "API_USER_POST")
    public JsonResponse getUserById(@PathVariable Integer id) {
        return JsonResponse.success(userService.getUserById(id));
    }

    @PutMapping(value = "/user/{id}", name = "API_USER_PUT")
    public JsonResponse updateUser(@PathVariable Integer id, @Valid RequestUser requestUser) throws Exception {
        userService.updateUser(requestUser);
        return JsonResponse.success("修改成功");
    }

}
