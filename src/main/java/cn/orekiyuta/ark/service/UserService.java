package cn.orekiyuta.ark.service;

import cn.orekiyuta.ark.mapper.UserMapper;
import cn.orekiyuta.ark.model.User;
import cn.orekiyuta.ark.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by orekiyuta on  2019/10/23 - 11:52
 **/
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);

        if(users.size() == 0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            User dbUser = users.get(0);
//            dbUser.setGmtCreate(System.currentTimeMillis());
//            dbUser.setName(user.getName());
//            dbUser.setAvatarUrl(user.getAvatarUrl());
//            dbUser.setToken(user.getToken());

            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setName(user.getName());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setToken(user.getToken());
            updateUser.setBio(user.getBio());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
//            userMapper.update(dbUser);
        }
    }


}
