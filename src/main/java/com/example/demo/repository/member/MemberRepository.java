package com.example.demo.repository.member;

import com.example.demo.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);

    Member findByMemberName(String memberName);
}
