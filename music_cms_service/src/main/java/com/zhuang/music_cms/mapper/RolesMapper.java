package com.zhuang.music_cms.mapper;

import com.zhuang.music_cms.model.entity.Roles;
import com.zhuang.music_cms.model.vo.ResponseRoles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package       : com.zhuang.music_cms.mapper
 * @InterfaceName : RolesMapper
 * @Description   : 角色操作接口
 * @Author        : Zhuang
 * @Date          : 2020-04-27 0:52
 */

@Mapper
public interface RolesMapper {

    public List<Roles> getRolesAll(String search);

    public List<ResponseRoles> getRolesByUserId(Integer userId);

    public List<ResponseRoles> getNoRoles(Integer userId);

    public void deleteRolesByUserId(@Param("userId") Integer userId, @Param("rolesId") List<Integer> rolesId);

    public void addRolesByUserId(@Param("userId") Integer userId, @Param("rolesId") List<Integer> rolesId);

    public void addRoles(Roles roles);

    public void deleteRoles(Integer id);

    public Roles getRolesById(Integer id);

    public void updateRoles(Roles roles);

}
