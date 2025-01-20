package me.ahngeunsu.springbootdeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
    //기존 로그인 페이지
public class UserViewController {
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

    //oauth 관련 로그인 페이지
    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/signup")
    public String signup() {

        return "signup";
    }
}
