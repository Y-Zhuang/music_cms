package com.zhuang.music_cms.service;

import com.github.pagehelper.PageInfo;
import com.zhuang.music_cms.model.entity.User;
import com.zhuang.music_cms.model.vo.RequestUser;

import java.io.IOException;
import java.util.List;

/**
 * @Package       : com.zhuang.music_cms.service
 * @InterfaceName : UserService 
 * @Description   : 用户Service接口
 * @Author        : Zhuang
 * @Date          : 2020-04-21 20:57
 */
public interface UserService {

    public PageInfo<User> getUserAll(int pageCode, int pageSize, String search);

    public String login(String phoneNumber ,String password);

    public void addUser(RequestUser requestUser) throws Exception;

    public User getUserByPhoneNumber(String phoneNumber);

    public void deleteUser(Integer id) throws Exception;

    public User getUserById(Integer id);

    public void updateUser(RequestUser requestUser) throws Exception;

}
