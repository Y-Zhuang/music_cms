package com.zhuang.music_cms.service;

import com.zhuang.music_cms.model.entity.Authority;
import com.zhuang.music_cms.model.vo.ResponseAuthority;

import java.util.List;

/**
 * @Package       : com.zhuang.music_cms.service
 * @InterfaceName : AuthorityService 
 * @Description   : 权限Service接口
 * @Author        : Zhuang
 * @Date          : 2020-04-27 0:53
 */
public interface AuthorityService {

    public List<ResponseAuthority> getMenuAuthorityByUserId(Integer userId, Integer superiorId);

    public List<ResponseAuthority> getViewAuthorityBySuperiorId(Integer userId, Integer superiorId);

    public List<ResponseAuthority> getAuthority();

    public List<Authority> getAuthorityAll();

    public List<Integer> getAuthorityIdByRolesId(Integer rolesId);

    public void updateAuthorityByRolesId(Integer rolesId, List<Integer> authorityId) throws Exception;

}
