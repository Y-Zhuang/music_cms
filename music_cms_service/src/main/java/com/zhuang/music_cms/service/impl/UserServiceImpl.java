package com.zhuang.music_cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuang.music_cms.config.AliyunOSSConfig;
import com.zhuang.music_cms.enums.ExceptionType;
import com.zhuang.music_cms.enums.FileType;
import com.zhuang.music_cms.exception.CustomException;
import com.zhuang.music_cms.mapper.AuthorityMapper;
import com.zhuang.music_cms.mapper.UserMapper;
import com.zhuang.music_cms.model.entity.User;
import com.zhuang.music_cms.model.vo.RequestUser;
import com.zhuang.music_cms.service.UserService;
import com.zhuang.music_cms.utils.AliyunOSSUtils;
import com.zhuang.music_cms.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package     : com.zhuang.music_cms.service.impl
 * @ClassName   : UserServiceImpl
 * @Description : 用户Service实现类
 * @Author      : Zhuang
 * @Date        : 2020-04-21 20:58
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AliyunOSSConfig aliyunOSSConfig;

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    @Override
    public PageInfo<User> getUserAll(int pageCode, int pageSize, String search) {
        PageHelper.startPage(pageCode, pageSize);
        List<User> list = userMapper.getUserAll(search);
        return new PageInfo<User>(list);
    }

    @Override
    public String login(String phoneNumber, String password) {
        User user = userMapper.getUserByPhoneNumber(phoneNumber);
        if (user != null && user.getPassword().equals(password)) {
            Map<String, Object> map = new HashMap<>();
            map.put("apis", authorityMapper.getUserApi(user.getId()));
            return jwtUtils.createJwt(user.getId(), user.getUserName(), map);
        }
        return null;
    }

    @Override
    public void addUser(RequestUser requestUser) throws Exception {
        User user = (User) requestUser;
        if (getUserByPhoneNumber(user.getPhoneNumber()) == null) {
            user.setCreateTime(LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
            if (requestUser.getImagesFile() != null) {
                try {
                    user.setUserImage(aliyunOSSConfig.getDomainApp() + aliyunOSSUtils.uploadFile(requestUser.getImagesFile(), aliyunOSSConfig.getBucketApp(), FileType.IMG.getMessage()));
                } catch (Exception e) {
                    throw new CustomException(ExceptionType.SYSTEM_ERROR, "添加失败");
                }
            }
            userMapper.addUser(user);
        } else {
            throw new CustomException(ExceptionType.USER_INPUT_ERROR, "手机号已存在");
        }
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userMapper.getUserByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteUser(Integer id) throws Exception {
        User user = this.getUserById(id);
        if (user != null) {
            try {
                if (user.getUserImage() != null && !"".equals(user.getUserImage())) {
                    aliyunOSSUtils.deleteFile(user.getUserImage().replace(aliyunOSSConfig.getDomainApp(), ""), aliyunOSSConfig.getBucketApp());
                }
                userMapper.deleteUser(id);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new CustomException(ExceptionType.USER_INPUT_ERROR, "删除失败");
            }
        }
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public void updateUser(RequestUser requestUser) throws Exception {
        User user = (User) requestUser;
        if (requestUser.getImagesFile() != null) {
            try {
                if (user.getUserImage() != null && !"".equals(user.getUserImage())) {
                    aliyunOSSUtils.deleteFile(user.getUserImage().replace(aliyunOSSConfig.getDomainApp(), ""), aliyunOSSConfig.getBucketApp());
                }
                user.setUserImage(aliyunOSSConfig.getDomainApp() + aliyunOSSUtils.uploadFile(requestUser.getImagesFile(), aliyunOSSConfig.getBucketApp(), FileType.IMG.getMessage()));
            } catch (Exception e) {
                throw new CustomException(ExceptionType.SYSTEM_ERROR, "修改失败");
            }
        }
        userMapper.updateUser(user);
    }
}
