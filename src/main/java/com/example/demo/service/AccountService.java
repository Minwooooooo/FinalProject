package com.example.demo.service;

import com.example.demo.domain.Account;
import com.example.demo.dto.request.AccountRequestDto;
import com.example.demo.dto.request.LoginRequestDto;
import com.example.demo.global.dto.ResponseDto;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountServiceImpl {

    private final AccountRepository accountRepository;
    private final JwtUtil jwtUtil;
//    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public ResponseDto<?> signup(AccountRequestDto reqDto) {
        try {
            if (accountRepository.findByEmail(reqDto.getEmail()).isPresent()){
                return ResponseDto.fail("OVERLAP_EMAIL", "SignUp Fail Cause Overlap");
            }
        }catch (Exception exception){
            throw new NoResultException();
        }

        Account account = new Account(reqDto);

        return ResponseDto.success(HttpStatus.OK.value());
    }

    @Override
    @Transactional
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Account account= accountRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new NoResultException()
        );

//        @todo Token auth

        TokenDto tokenDto= jwtUtil.createAllToken(account.getEmail());

        return null;
    }
}
