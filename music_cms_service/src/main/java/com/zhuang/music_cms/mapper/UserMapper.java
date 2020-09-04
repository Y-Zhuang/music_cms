package com.zhuang.music_cms.mapper;

import com.zhuang.music_cms.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Package       : com.zhuang.music_cms.mapper
 * @InterfaceName : UserMapper 
 * @Description   : 用户操作接口
 * @Author        : Zhuang
 * @Date          : 2020-04-21 20:54
 */

@Mapper
public interface UserMapper {

    public List<User> getUserAll(String search);

    public User getUserByPhoneNumber(String phoneNumber);

    public void addUser(User user);

    public void deleteUser(Integer id);

    public User getUserById(Integer id);

    public void updateUser(User user);

}
