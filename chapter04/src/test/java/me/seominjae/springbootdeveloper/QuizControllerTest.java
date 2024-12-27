package me.seominjae.springbootdeveloper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc

class QuizControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void mockMvcSetup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();       //builder().build()의 응용 형태
    }
    // 여기서 문제
    @DisplayName("quiz() : GET /quiz?code=1 이면 응답 코드는 201, 응답 본문은 Created!를 반환")
    @Test
    public void getQuiz1() throws Exception{
        // given
        final String url = "/quiz";
        // when
        final ResultActions result = mockMvc.perform(get(url)
                .param("code","1")      //values 가 "1"인 이유는 JSON 이라서
        );
        // then
        result.andExpect(status().isCreated())
                .andExpect(content().string("Created!"));
    }
    @DisplayName("quiz() : GET /quiz?code=2 이면 응답 코드는 400, 응답 본문은 Bad Request를 반환")
    @Test
    public void getQuiz2() throws Exception{
    final String url = "/quiz";
        final ResultActions result = mockMvc.perform(get(url)
                .param("code","2")
        );
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("Bad Request!"));
    }
    @DisplayName("quiz() : POST /quiz?code=1 이면 응답 코드는 403, 응답 본문은 Forbidden!을 반환한다.")
    @Test
    public void postQuiz() throws Exception{
        //given
        final String url = "/quiz";
        //when
        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Code(1)))
        );
        //then
        result.andExpect(status().isForbidden())
                .andExpect(content().string("Forbidden!"));
    }
    @DisplayName("quiz() : POST /quiz?code=99 이면 응답 코드는 200, 응답 본문은 OK!을 반환한다.")
    @Test
    public void postQuiz2() throws Exception{
        final String url = "/quiz";
        //when
        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Code(99)))
        );
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string("OK!"));
    }
}
/*
    ObjectMapper : Jackson 라이브러리에서 제공하는 클래스로 객체와 JSON 간 변환을 처리

    객체 - JSON 문자열로의 변환을 '객체 직렬화' 라고 하기도 합니다.

    JSON 문자열로의 반환 결과를 보고싶다면 포스트맨
*/