package com.example.demo.security.jwt;


import com.example.demo.dto.responseDto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerException implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
                     AccessDeniedException accessDeniedException) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().println(
            new ObjectMapper().writeValueAsString(
                    ResponseDto.fail("BAD_REQUEST_403", "로그인이 필요합니다!")
            )
    );
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
  }
}
