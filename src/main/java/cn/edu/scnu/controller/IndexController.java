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





}
