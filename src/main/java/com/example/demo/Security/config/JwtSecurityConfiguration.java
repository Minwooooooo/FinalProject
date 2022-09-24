package com.example.demo.Security.config;

import com.example.demo.Security.Jwt.JwtCustomFilter;
import com.example.demo.Security.Jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider jwtTokenProvider;


    //JwtCustomFilter를 DefaultSecurityFilterChain에 등록
    @Override
    public void configure(HttpSecurity httpSecurity){
        JwtCustomFilter jwtCustomFilter = new JwtCustomFilter(jwtTokenProvider);
        //순서 설정 : JwtCustomFilter가 UsernamePasswordAuthenticationFilter이전에 시행
        httpSecurity.addFilterBefore(jwtCustomFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
