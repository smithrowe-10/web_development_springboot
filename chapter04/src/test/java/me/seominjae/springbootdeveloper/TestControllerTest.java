package me.seominjae.springbootdeveloper;
/*
    Test 클래스 만드는 방법

    1) 테스트하고자 하는 클래스를 연다
    2) 클래스 명에 alt + enter 누르면 팝업이 뜬다
    3) create test 선택
    4) JUnit5 써야됨
 */
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest     // 테스트용 애너테이션 컨텍스트 생성
@AutoConfigureMockMvc   // MockMvc 생성 및 자동 구성 - 목업

class TestControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    //위 부분은 객체 생성
    //밑 부분은 메서드
    @BeforeEach
    public void mockMvcSetup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();       //builder().build()의 응용 형태
    }

    @AfterEach
    public void cleanUp(){
        memberRepository.deleteAll();
    }

    @DisplayName("getAllMembers: 아티클 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception {  //throws Exception : 예외로 처리함
        // given
        final String url = "/test";     //앞으로 자주 나오는 방식
        Member savedMember = memberRepository.save(new Member(1L, "홍길동"));
        // maybeags/web_development_java 에 c15_casting 의 CentralControl.java / Main.java 확

        // when
        final ResultActions result = mockMvc.perform(get(url)       //(1)
                .accept(MediaType.APPLICATION_JSON));               //(2)

        // then
        result.andExpect(status().isOk())                           //(3)
                //                                                    (4)
                .andExpect(jsonPath("$[0].id").value(savedMember.getId()))
                .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
    }
}
/*
    (1) : perform() 메서드는 요청을 전송하는 역할을 하는 메서드.
        return 값으로 ResultActions 객체를 받음.
        ResultActions 객체는 반환값을 검증하고 확인하는 andExpect() 메서드를 제공함.

    (2) : accept() 메서드는 요청을 보낼 때 무슨 타입으로 '응답을 받을지' 결정하는 메서드.
        저희는 주로 JSON 을 이용할 예정

    (3) : andExpert() 메서드는 응답을 검증. TestController 에서 만든 API 는 응답으로
        OK(200)을 반환하므로 이에 해당하는 메서드인 isOk() 를 사용해 응답 코드가 200(OK) 인지 확인

    (4) : jsonPath("$[0].{필드명}")은 JSON 응답값의 값(value)을 가져오는 역할을 하는 메서드
        0(인덱스)번째 배열에 들어 있는 객체의 id, name 의 값을 가져오고 저장된 값과 같은지 확인
        (memberRepository.savedMember.getId() 등을 이용해서)


        다 하신 분들은
            테스트 코드 패턴을 연습하기위해
            src-main-java-me.seominjae-springbootdeveloper 폴더에
            QuizController.java

 */
/*
    @SpringBootTest :
        애플리케이션 클래스에
        추가하는 애너테이션인 @SpringBootApplication 이
        있는 클래스를 찾고, 그 클래스에 포함돼 있는 빈을 찾은 다음,
        테스트용 애플리케이션 컨텍스트라는 것을 생성합니다.

    @AutoConfigureMockMvc :
        MockMvc 를 생성하고 자동으로 구성하는 애너테이션으로,
        이 것은 애플리케이션을 서버에 배포하지 않고도 테스트용 MVC 환경을 만들어
        요청, 전송, 응답 기능을 제공하는 유틸리티 클래스.
            즉, '컨트롤러를 테스트할 때 사용되는 클래스'
 */