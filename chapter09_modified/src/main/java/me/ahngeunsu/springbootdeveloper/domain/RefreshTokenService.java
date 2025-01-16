package me.ahngeunsu.springbootdeveloper.domain;

import lombok.RequiredArgsConstructor;
import me.ahngeunsu.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("unexpected token"));
    }
    // TokenService. java 만들어서 리프레시 토큰 유효선 검사 진행하고, 리프레시 토큰으로 유저 ID 찾기
    // 유저 Id로 해당 유저 찾은 후에 generateToken() 메서드 호출을 통해 새로운 액세스 토큰을 생성
}
