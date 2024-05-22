package cn.edu.scnu.service;

import cn.edu.scnu.common.MD5Util;
import cn.edu.scnu.entity.TbUser;
import cn.edu.scnu.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, TbUser> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public TbUser findUserByUsername(String username){
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<TbUser>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    public TbUser login(String username, String password) {
        TbUser user = findUserByUsername(username);
        if (user != null && user.getPassword().equals(MD5Util.md5(password))) {
            return user;
        }
        return null;
    }
}
