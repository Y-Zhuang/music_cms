package com.zhuang.music_cms.mapper;

import com.zhuang.music_cms.model.entity.Authority;
import com.zhuang.music_cms.model.vo.ResponseAuthority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package       : com.zhuang.music_cms.mapper
 * @InterfaceName : AuthorityMapper 
 * @Description   : 权限操作接口
 * @Author        : Zhuang
 * @Date          : 2020-04-27 0:53
 */

@Mapper
public interface AuthorityMapper {

    public List<ResponseAuthority> getAuthorityByUserId(@Param("userId")Integer userId, @Param("type") String type, @Param("superiorId") Integer superiorId);

    public List<ResponseAuthority> getAuthority();

    public List<Authority> getAuthorityAll();

    public List<Integer> getAuthorityIdByRolesId(Integer rolesId);

    public void addAuthorityByRolesId(@Param("rolesId") Integer rolesId, @Param("authorityId") List<Integer> authorityId);

    public void deleteAuthorityByRolesId(@Param("rolesId") Integer rolesId, @Param("authorityId") List<Integer> authorityId);

    public List<String> getUserApi(Integer userId);

}
