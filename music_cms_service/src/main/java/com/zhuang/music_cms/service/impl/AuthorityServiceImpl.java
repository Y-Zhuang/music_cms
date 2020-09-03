package com.zhuang.music_cms.service.impl;

import com.zhuang.music_cms.enums.ExceptionType;
import com.zhuang.music_cms.exception.CustomException;
import com.zhuang.music_cms.mapper.AuthorityMapper;
import com.zhuang.music_cms.model.entity.Authority;
import com.zhuang.music_cms.model.vo.ResponseAuthority;
import com.zhuang.music_cms.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Package     : com.zhuang.music_cms.service.impl
 * @ClassName   : AuthorityServiceImpl 
 * @Description : 权限Service实现类
 * @Author      : Zhuang
 * @Date        : 2020-04-27 0:54
 */

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public List<ResponseAuthority> getMenuAuthorityByUserId(Integer userId, Integer superiorId) {
        return authorityMapper.getAuthorityByUserId(userId, "menu", superiorId);
    }

    @Override
    public List<ResponseAuthority> getViewAuthorityBySuperiorId(Integer userId, Integer superiorId) {
        return authorityMapper.getAuthorityByUserId(userId, "view", superiorId);
    }

    @Override
    public List<ResponseAuthority> getAuthority() {
        return authorityMapper.getAuthority();
    }

    @Override
    public List<Authority> getAuthorityAll() {
        return authorityMapper.getAuthorityAll();
    }

    @Override
    public List<Integer> getAuthorityIdByRolesId(Integer rolesId) {
        return authorityMapper.getAuthorityIdByRolesId(rolesId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateAuthorityByRolesId(Integer rolesId, List<Integer> authorityId) throws Exception {
        try {
            authorityMapper.deleteAuthorityByRolesId(rolesId, authorityId);
            authorityMapper.addAuthorityByRolesId(rolesId, authorityId);
        } catch (Exception ignored) {
            throw new CustomException(ExceptionType.USER_INPUT_ERROR, "分配失败");
        }
    }
}
