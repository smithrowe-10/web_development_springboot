package me.seominjae.springbootdeveloper.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String email;
    private String password;

    /*
        02 단계 - AddUserRequest 객체를 argument로 받는 회원 정보 추가 메서드를 작성할 예정
            service 디렉토리에 UserService.java 파일을 생성하고 코드 작성
     */
}
