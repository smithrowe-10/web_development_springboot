package me.ahngeunsu.springbootdeveloper.config.oauth;

import lombok.RequiredArgsConstructor;
import me.ahngeunsu.springbootdeveloper.domain.User;
import me.ahngeunsu.springbootdeveloper.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override       //alt + insert 눌러서 override 한 메서드 -> 재정의
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        saveOrUpdate(user);

        return user;
    }

    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");        // 다운캐스팅
        String name = (String) attributes.get("name");        // 다운캐스팅

        User user = userRepository.findByEmail(email)
                .map(entitiy -> entitiy.update(name))
                .orElse(User.builder()
                        .email(email)
                        .nickname(name)
                        .build());  // User 정보 없으면 새로 저장 -> builder 패턴으로 생성

        return userRepository.save(user);
    }
}

/*
    부모 클래스인 DefaultOAuth2UserService 에서 제공하는 OAuth 서비스에서 제공하는 정보를 기반으로
    유저 객체를 만들어 주는 loadUser() 메서드 사용 객체를 불러왔습니다.(override)

    구글을 기준으로 했을 때 사용자 객체 내부는
        식별자,
        이름
        이메일
        프로필 사진 링크 등의 정보 포함.

    saveOrUpdate() 메서드를 통해 사용자가 users 테이블에 존재하면 업데이트 존재하지 않으면 새로 저장

    OAuth2 설정 파일을 작성할 예정
        -> OAuth2 를 사용한다는게 거의 기본적으로 JWT 와 연계가 되는데
        기존 스프링 시큐리티를 구현하면서 작성한 설정이 아니라 추가적인 작업이 필요하다는 의미

        -> 기존의 폼 로그인 방식을 구현하려고 사용한 설정을 다 갈아엎어야 합니다.
        webSecurityConfig.java 를 다 주석처리

        config 패키지 내에 WebOAuthSecurityConfig.java 파일 생성
 */
