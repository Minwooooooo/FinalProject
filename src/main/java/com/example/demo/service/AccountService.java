package com.example.demo.service;

import com.example.demo.domain.Account;
import com.example.demo.dto.request.AccountRequestDto;
import com.example.demo.dto.request.LoginRequestDto;
import com.example.demo.global.dto.ResponseDto;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountServiceImpl {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
//    private final JwtTuil jwtTuil;
//    private final RefreshTokenRepository refreshTokenRepository;

    @Override
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
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        return null;
    }
}
