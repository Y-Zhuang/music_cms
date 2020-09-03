package com.zhuang.music_cms.service;

import com.github.pagehelper.PageInfo;
import com.zhuang.music_cms.model.entity.Roles;

import java.util.List;
import java.util.Map;

/**
 * @Package       : com.zhuang.music_cms.service
 * @InterfaceName : RolesService 
 * @Description   : 角色Service接口
 * @Author        : Zhuang
 * @Date          : 2020-04-27 0:55
 */
public interface RolesService {

    public PageInfo<Roles> getRolesAll(int pageCode, int pageSize, String search);

    public Map<String,Object> getRolesByUserId(Integer userId);

    public void updateRolesByUserId(Integer userId, List<Integer> putRolesId, List<Integer> cutRolesId);

    public void addRoles(Roles roles) throws Exception;

    public void deleteRoles(Integer id) throws Exception;

    public Roles getRolesById(Integer id);

    public void updateRoles(Roles roles) throws Exception;

}
