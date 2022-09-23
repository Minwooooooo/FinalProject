package com.example.demo.service.impl;

import javax.servlet.http.HttpServletResponse;

public interface KakaoServiceImpl {
    void kakaoLogin(String code, HttpServletResponse response);
}
