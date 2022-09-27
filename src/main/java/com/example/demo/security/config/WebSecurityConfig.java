package com.example.demo.security.config;

import com.example.demo.security.jwt.AccessDeniedHandlerException;
import com.example.demo.security.jwt.AuthenticationEntryPointException;
import com.example.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Web Security 설정(debug = true)
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AccessDeniedHandlerException accessDeniedHandlerException;
    private final AuthenticationEntryPointException authenticationEntryPointException;
    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // 기본값이 on인 csrf 취약점 보안을 해제한다. on으로 설정해도 되나 설정할경우 웹페이지에서 추가처리가 필요함.
            .headers()
                .frameOptions().sameOrigin() // SockJS는 기본적으로 HTML iframe 요소를 통한 전송을 허용하지 않도록 설정되는데 해당 내용을 해제한다.
            .and()
                .formLogin() // 권한없이 페이지 접근하면 로그인 페이지로 이동한다.
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointException)
                .accessDeniedHandler(accessDeniedHandlerException)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
                .anyRequest().permitAll()

            .and()
                .apply(new JwtSecurityConfiguration(jwtTokenProvider));


    }

}
