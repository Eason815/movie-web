package cn.edu.scnu.controller;

import cn.edu.scnu.entity.TbUser;
import cn.edu.scnu.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/doLogin")
    public String doLogin(String username, String password, HttpSession session){
        TbUser user = userService.findUserByUsername(username);
        if(user !=null && user.getPassword().equals(DigestUtils.md5Hex(password))) {
            session.setAttribute("user", user);
            return "登录成功！";
        }else
            return "登录失败！";
    }

    @RequestMapping("/doRegister")
    public String doRegister(TbUser user,String password2, HttpSession session) {
        if (!user.getPassword().equals(password2))
            return "两次密码不一致！";
        TbUser user1 = userService.findUserByUsername(user.getUsername());
        if (user1 != null)
            return "用户名已存在！";
        else {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));//密码加密
            userService.save(user);//保存用户
            session.setAttribute("user", user);//注册成功后自动登录
            return "注册成功！";
        }
    }
}
