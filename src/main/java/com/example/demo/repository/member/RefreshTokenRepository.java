package com.example.demo.repository.member;

import com.example.demo.entity.member.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
}
