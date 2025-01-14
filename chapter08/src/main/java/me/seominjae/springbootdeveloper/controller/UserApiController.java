package me.seominjae.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.seominjae.springbootdeveloper.dto.AddUserRequest;
import me.seominjae.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request){
        userService.save(request);  // 회원 가입 메서드 호출
        return "redirect:/login";   // 회원 가입 완료된 이후에 로그인 페이지로 이동
    }
    /*
        회원 가입 절차가 완료된 이후에 로그인 페이지로 이동하기 위해 "redirect:" 접두사를 붙힘
        이렇게 하면 회원가입 절차가 끝나고 강제로 /login url 에 해당하는 화면으로 이동

        회원 가입, 로그인 뷰를 작성할예정.

            뷰 관련 컨트롤러를 구현
            동일 패키지에 UserViewController.java 파일을 만들겠음.
     */
}
