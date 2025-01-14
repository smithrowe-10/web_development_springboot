package me.seominjae.springbootdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExampleController {

    @GetMapping("thymeleaf/example")
    public String thymeleafExample(Model model) {
        Person examplePerson = new Person();

        examplePerson.id = 1L;
        examplePerson.name = "홍길동";
        examplePerson.age = 11;
        examplePerson.setHobbies(List.of("운동", "독서"));

        //이상에서 Person 클래스의 인스턴스에 값 대입
        model.addAttribute("person",examplePerson);
        model.addAttribute("today", LocalDate.now());

        return "example";   //example.html 이라는 뷰를 조회합니다.
    }

    @Setter
    @Getter
    class Person{
        private long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}
