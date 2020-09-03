package com.zhuang.music_cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuang.music_cms.enums.ExceptionType;
import com.zhuang.music_cms.exception.CustomException;
import com.zhuang.music_cms.mapper.RolesMapper;
import com.zhuang.music_cms.model.entity.Roles;
import com.zhuang.music_cms.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package     : com.zhuang.music_cms.service.impl
 * @ClassName   : RolesServiceImpl 
 * @Description : 权限Service实现类
 * @Author      : Zhuang
 * @Date        : 2020-04-27 0:57
 */

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesMapper rolesMapper;

    @Override
    public PageInfo<Roles> getRolesAll(int pageCode, int pageSize, String search) {
        PageHelper.startPage(pageCode, pageSize);
        List<Roles> list = rolesMapper.getRolesAll(search);
        return new PageInfo<Roles>(list);
    }

    @Override
    public Map<String, Object> getRolesByUserId(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userRoles", rolesMapper.getRolesByUserId(userId));
        map.put("userNotRoles", rolesMapper.getNoRoles(userId));
        return map;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateRolesByUserId(Integer userId, List<Integer> putRolesId, List<Integer> cutRolesId) {
        try {
            if (cutRolesId.size() != 0) {
                rolesMapper.deleteRolesByUserId(userId, cutRolesId);
            }
            if (putRolesId.size() != 0) {
                rolesMapper.addRolesByUserId(userId, putRolesId);
            }
        } catch (Exception e) {
            throw new CustomException(ExceptionType.USER_INPUT_ERROR, "分配失败");
        }
    }

    @Override
    public void addRoles(Roles roles) throws Exception {
        try {
            roles.setCreateTime(LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
            rolesMapper.addRoles(roles);
        } catch (Exception e) {
            throw new CustomException(ExceptionType.USER_INPUT_ERROR, "添加失败");
        }
    }

    @Override
    public void deleteRoles(Integer id) throws Exception {
        try {
            rolesMapper.deleteRoles(id);
        } catch (Exception e) {
            throw new CustomException(ExceptionType.USER_INPUT_ERROR, "删除失败");
        }
    }

    @Override
    public Roles getRolesById(Integer id) {
        return rolesMapper.getRolesById(id);
    }

    @Override
    public void updateRoles(Roles roles) throws Exception {
        try {
            rolesMapper.updateRoles(roles);
        } catch (Exception e) {
            throw new CustomException(ExceptionType.USER_INPUT_ERROR, "修改失败");
        }
    }

}
