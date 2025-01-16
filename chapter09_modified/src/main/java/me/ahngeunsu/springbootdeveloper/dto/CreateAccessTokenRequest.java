package me.ahngeunsu.springbootdeveloper.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class CreateAccessTokenRequest {
        private String refreshToken;
}
