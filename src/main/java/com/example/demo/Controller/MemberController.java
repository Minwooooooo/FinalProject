package com.example.demo.Controller;

import com.example.demo.Dto.ResponseDto.ResponseDto;
import com.example.demo.Service.KakaoLoginService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final KakaoLoginService kakaoLoginService;

    @GetMapping(value = "/login/kakao")
    public ResponseDto<?> login(@RequestParam String code, HttpServletResponse httpServletResponse) throws IOException, ParseException {

        //E001: https로 로그인시 오류
        return kakaoLoginService.kakaoLogin(code,httpServletResponse);
    }

}
