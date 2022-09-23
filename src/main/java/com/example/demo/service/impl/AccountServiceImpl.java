package com.example.demo.service.impl;

import com.example.demo.dto.request.AccountRequestDto;
import com.example.demo.dto.request.LoginRequestDto;
import com.example.demo.global.dto.ResponseDto;

import javax.servlet.http.HttpServletResponse;

public interface AccountServiceImpl {
    ResponseDto<?> singup(AccountRequestDto reqDto);

    ResponseDto<?> signup(AccountRequestDto reqDto);

    ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response);
}
