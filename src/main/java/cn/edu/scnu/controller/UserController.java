package cn.edu.scnu.controller;

import cn.edu.scnu.common.MD5Util;
import cn.edu.scnu.entity.TbUser;
import cn.edu.scnu.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/toLoginPage")
    public String toLoginPage(Model model){
        // 传递当前年份到前端
        model.addAttribute("currentYear" , Calendar.getInstance().get(Calendar.YEAR));
        return "login";
    }
    @RequestMapping("/toLogOut")
    public String logOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/index";
    }
    @RequestMapping("/toRegisterPage")
    public String toRegisterPage(Model model){
        model.addAttribute("currentYear" , Calendar.getInstance().get(Calendar.YEAR));
        return "register";
    }
    @RequestMapping("/doLogin")
    @ResponseBody
    public String doLogin(String username, String password, HttpSession session) {
        TbUser user1 = userService.login(username, password);
        if (user1 != null) {
            user1.setPassword(""); //去敏
            session.setAttribute("user", user1);
            return "登录成功!";
        } else {
            return "登录失败!";
        }
    }

    @RequestMapping("/doRegister")
    @ResponseBody
    public String doRegister(String username, String password, String password2) {
        TbUser user = userService.getById(username);
        if(user != null){
            return "用户名已存在!";
        }
        if (!password.equals(password2)) {
            return "两次密码不一致!";
        }
        TbUser user1 = new TbUser();
        user1.setUsername(username);
        user1.setPassword(MD5Util.md5(password));
        userService.save(user1);

        return "注册成功!";
    }
}
