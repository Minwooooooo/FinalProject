package com.example.demo.repo;

import com.example.demo.model.EnterMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterMemberRepository extends JpaRepository<EnterMember, String> {

    EnterMember findBySessionId(String sessionId);


}
