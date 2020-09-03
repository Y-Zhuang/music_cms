package com.zhuang.music_cms.controller;

import com.zhuang.music_cms.exception.JsonResponse;
import com.zhuang.music_cms.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Package     : com.zhuang.music_cms.controller
 * @ClassName   : AuthorityController 
 * @Description : 权限控制器
 * @Author      : Zhuang
 * @Date        : 2020-04-27 1:00
 */

@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/menu/{userId}/{superiorId}")
    public JsonResponse getMenuAuthorityByUserId(@PathVariable("userId") Integer userId, @PathVariable("superiorId") Integer superiorId) {
        return JsonResponse.success(authorityService.getMenuAuthorityByUserId(userId, superiorId));
    }

    @GetMapping("/view/{userId}/{superiorId}")
    public JsonResponse getViewAuthorityBySuperiorId(@PathVariable("userId") Integer userId, @PathVariable("superiorId") Integer superiorId) {
        return JsonResponse.success(authorityService.getViewAuthorityBySuperiorId(userId, superiorId));
    }

    @GetMapping("authority/roles")
    public JsonResponse getAuthority() {
        return JsonResponse.success(authorityService.getAuthority());
    }

    @GetMapping(value = "authority", name = "API_AUTHORITY_GET")
    public JsonResponse getAuthorityAll() {
        return JsonResponse.success(authorityService.getAuthorityAll());
    }

    @GetMapping("authority/{rolesId}")
    public JsonResponse getAuthorityIdByRolesId(@PathVariable("rolesId") Integer rolesId) {
        return JsonResponse.success(authorityService.getAuthorityIdByRolesId(rolesId));
    }

    @PostMapping(value = "authority/{rolesId}", name = "API_ROLES_AUTHORITY_POST")
    public JsonResponse updateAuthorityByRolesId(@PathVariable("rolesId") Integer rolesId, @RequestParam("authorityId") List<Integer> authorityId) throws Exception {
        authorityService.updateAuthorityByRolesId(rolesId, authorityId);
        return JsonResponse.success("分配成功");
    }

}
