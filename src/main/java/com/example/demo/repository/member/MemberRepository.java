package com.example.demo.repository.member;

import com.example.demo.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByKakaoId(Long id);
    Optional<Member> findByNaverId(String id);

    boolean existsByKakaoId(Long id);

    boolean existsByNaverId(String id);


}
