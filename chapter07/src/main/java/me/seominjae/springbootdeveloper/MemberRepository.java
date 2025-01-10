package me.seominjae.springbootdeveloper;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이름으로 멤버를 찾는 메서드 정의 -> 이름이 없을 수도 있어서 class Optional 입니다.
    Optional<Member> findByName(String name);
    // 이상의 코드 작성 후에 MemberRepositoryTest.java 로 이동
}
