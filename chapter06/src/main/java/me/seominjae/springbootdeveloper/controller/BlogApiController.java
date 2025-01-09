package me.seominjae.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.seominjae.springbootdeveloper.domain.Article;
import me.seominjae.springbootdeveloper.dto.AddArticleRequest;
import me.seominjae.springbootdeveloper.dto.ArticleResponse;
import me.seominjae.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;

    // HTTP 메서드가 POST 일 때 전달 받은 URl과 동일하면 지금 정의하는 메서드와 매핑
    @PostMapping("/api/articles")
    // @ResponseBody로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }
    /*
        1. @RestController : 클래스에 붙이면 HTTP 응답으로 객체 데이터를 "JSON" 형식으로 반환
        2. @PostMapping() : HTTP 메서드가 POST일 때 요청 받은 URL과 동일한 메서드와 매핑.
            지금의 경우 /api/articles는 addArticle() 메서드와 매핑.
        3. @RequestBody : HTTP 요청을 할 때, 응답에 해당하는 값을 @RequestBody 애너테이션이 붙은
            대상 객체는 AddArticleRequest에 매핑
        4. ResponseEntity.status().body()는 응답 코드가 201, 즉 Created를 응답하고
            테이블에 저장된 객체를 반환합니다.

        200 OK : 요청이 성공적으로 수행되었음
        201 Created : 요청이 성공적으로 수행되었고, 새로운 리소스가 생성되었음
        400 Bad Request : 요청 값이 잘못되어 요청에 실패했음
        403 Forbidden : 권한이 없어 요청에 실패했음
        404 Not Found : 요청 값으로 찾은 리소스가 없어 요청에 실패했음
        500 Internal Server Error : 서버 상에 문제가 있어 요청에 실패했음

        API가 잘 동작하나 테스트를 해볼 예정

            H2 콘솔 활성화

            application.yml로 이동

        window 키 누르고 -> postman
        HTTP 메서드 : POST
        URL : http://localhost:8080/api/articles
        BODY : raw -> JSON
        그리고 요청창에
        {
            "title":"제목".
            "content":"내용"
        }
        으로 작성 후에 Send 버튼 눌러 요청을 해보세요.

        결과값이 Body에 pretty모드로 결과를 보여줬습니다.
        -> 여기까지 성공했다면 스프링 부트 서버에 저장된 것을 의미합니다.

        여기까지가 HTTP 메서드 POST 로 서버에 요청을 한 후에 값을 저장하는 과정에 해당

        이제 크롬 켜세요
        http://localhost:8080/h2-console

        SQL satatement 입력창에(SQL 편집기모양에)
        select * from ARTICLE
        그리고 RUN눌러서 쿼리실행
        hs 데이터 베이스에 저장된 데이터를 확인할 수 있습니다.
        애플리캐이션을 실행하면 자동으로 생성한 엔티티 내용을 바탕으로
        테이블이 생성되고,
        우리가 요청한 POST 요청에 의해 INSERT문이 실행되어
        실제로 데이터가 저장 된 겁니다.

        1/9일 안되는 분들 확인
        반복작업 줄일 테스트 코드 작성
            - 매번 H2 들어가는게 번거로워서
                test를 이용할 예정

                test 폴더에 BlogApiControllerTest.java를 만들기 위한 방법
                파일 내에 들어와서 public class BlogApiController 표기된 부분으로 들어가서
                클래스명 클릭 + alt + enter -> create test 가 있음.
     */

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok().body(articles);
    }
    /*
        /api/articles GET 요청이 들어오면 글 전체를 조회하는 findAll() 메서드를 호출
        -> 다음 응답용 객체인 ArticleResponse로 파싱해서 body에 담아
        클라이언트에게 전송합니다. -> 해당 코드에선 stream 적용ㅇㅇ

        * stream : 어러 데이터가 모여 있는 컬렉션을 간편하게 처리하기 위해서 사용하는 기능
            자바 8에 추가
     */
    @GetMapping("/api/articles/{id}")
    //URL 경로에서 값을 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) { // URL 에서 {id}에 해당하는 값이 id로 들어옴
        Article  article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }
    /*
        @PathVariable : URL에서 값을 가져오는 애너테이션.
            /api/articles/3 GET 요청을 받으면 id에 3이 argument로 들어오게 됩니다.
            그리고 이 값은 바로 전에 만든 서비스 클래스의 findById() 메서드로 넘어가서 3번 블로그 글을 찾아옵니다
            그리고 그 글을 찾으면 3번 글의 정보(제목 / 내용) 를 body에 담아서 웹브라우저 가지고 옵니다.
     */
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle (@PathVariable long id){
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
    /*
        @PathVariable 통해서 {id}에


     */

}
