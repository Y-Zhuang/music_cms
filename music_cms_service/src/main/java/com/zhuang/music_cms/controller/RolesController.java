package com.zhuang.music_cms.controller;

import com.zhuang.music_cms.enums.ExceptionType;
import com.zhuang.music_cms.exception.CustomException;
import com.zhuang.music_cms.exception.JsonResponse;
import com.zhuang.music_cms.model.entity.Roles;
import com.zhuang.music_cms.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Package     : com.zhuang.music_cms.controller
 * @ClassName   : RolesController 
 * @Description : 角色控制器
 * @Author      : Zhuang
 * @Date        : 2020-04-27 1:01
 */

@RestController
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @GetMapping(value = "/roles", name = "API_ROLES_GET")
    public JsonResponse getRolesAll(int pageCode, int pageSize, String search) {
        return JsonResponse.success(rolesService.getRolesAll(pageCode, pageSize, search));
    }

    @GetMapping("/roles/user/{userId}")
    public JsonResponse getRolesByUserId(@PathVariable("userId") Integer userId) {
        return JsonResponse.success(rolesService.getRolesByUserId(userId));
    }

    @PostMapping(value = "/roles/{userId}", name = "API_USER_ROLES_POST")
    public JsonResponse addUserRoles(@PathVariable("userId") Integer userId, @RequestParam("putRolesId") List<Integer> putRolesId, @RequestParam("cutRolesId") List<Integer> cutRolesId){
        rolesService.updateRolesByUserId(userId, putRolesId, cutRolesId);
        return JsonResponse.success("分配成功");
    }

    @PostMapping(value = "/roles", name = "API_ROLES_POST")
    public JsonResponse addRoles(@Valid Roles roles) throws Exception {
        rolesService.addRoles(roles);
        return JsonResponse.success("添加成功");
    }

    @DeleteMapping(value = "/roles/{id}", name = "API_ROLES_DELETE")
    public JsonResponse deleteRoles(@PathVariable Integer id) {
        try {
            rolesService.deleteRoles(id);
            return JsonResponse.success("删除成功");
        } catch (Exception e) {
            throw new CustomException(ExceptionType.SYSTEM_ERROR, "删除失败");
        }

    }

    @GetMapping(value = "/roles/{id}", name = "API_ROLES_GET")
    public JsonResponse getRolesById(@PathVariable Integer id) {
        return JsonResponse.success(rolesService.getRolesById(id));
    }

    @PutMapping(value = "/roles/{id}", name = "API_ROLES_PUT")
    public JsonResponse updateRoles(@PathVariable Integer id, @Valid Roles roles) throws Exception {
        rolesService.updateRoles(roles);
        return JsonResponse.success("修改成功");
    }

}
