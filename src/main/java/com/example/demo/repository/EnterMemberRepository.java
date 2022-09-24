package com.example.demo.repository;

import com.example.demo.Entity.EnterMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterMemberRepository extends JpaRepository<EnterMember, String> {

    EnterMember findBySessionId(String sessionId);


}
