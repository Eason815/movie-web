package cn.edu.scnu.service;

import cn.edu.scnu.entity.TbUser;
import cn.edu.scnu.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, TbUser> {
    @Autowired
    private UserMapper userMapper;
    public TbUser findUserByUsername(String username){
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<TbUser>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

}
