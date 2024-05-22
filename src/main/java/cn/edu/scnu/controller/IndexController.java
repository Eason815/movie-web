package cn.edu.scnu.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;

@Controller
public class IndexController {
    @RequestMapping("/index")
    public String toIndexPage(){
        return "index";
    }

    @RequestMapping("/toLoginPage")
    public String toLoginPage(Model model){
        // 传递当前年份到前端
        model.addAttribute("currentYear" , Calendar.getInstance().get(Calendar.YEAR));
        return "login";
    }

    @RequestMapping("/toLogOut")
    public String logOut(HttpSession session){
        session.setAttribute("user", null);
        return "index";
    }

    @RequestMapping("/toRegisterPage")
    public String toRegisterPage(Model model){
        model.addAttribute("currentYear" , Calendar.getInstance().get(Calendar.YEAR));
        return "register";
    }



}
