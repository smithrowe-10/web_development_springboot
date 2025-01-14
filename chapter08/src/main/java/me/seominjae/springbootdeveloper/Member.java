package me.seominjae.springbootdeveloper;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)      // 테이블 상에서의 컬럼 명이 id라는 의미
    private Long id;    // DB 테이블의 'id' 컬럼과 매칭

    @Column(name = "name", nullable = false)   // 테이블 상에서의 컬럼 명이 name이라는 의미
    private String name;    // DB 테이블의 'name' 컬럼과 매칭

    public void changeName(String name){
        this.name = name;
    }

}
