package com.example.demo.service;

import com.example.demo.Entity.Member;
import com.example.demo.repository.MemberRepository;


import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.dto.responseDto.ResponseDto;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    final static String BE_SERVER = "localhost:8080";

    public ResponseDto<?> kakaoLogin(String code, HttpServletResponse response) throws IOException, ParseException {
        // 0. FE에서 코드받기
        //https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=6d51c4942788dc0abd939ace6ee2d8b7&redirect_uri=http://localhost:3000/login/kakao


        // 1. 토큰 받기
        String Accesstoken=getKakaoToken(code);
        // 2. 정보 받기
        JsonObject memberInfo=getInfo(Accesstoken);
        // 3. 기존 가입 여부 확인
        if(!chekExistMember(memberInfo)){
            // false : 신규 회원
            // 회원가입
            signIn(memberInfo);
        }
        // 4. 로그인(Jwt토큰 생성)
        Member member = memberRepository.findById(Long.valueOf(memberInfo.get("id").toString())).get();
        String accessToken = jwtTokenProvider.creatToken(member);
        System.out.println(accessToken);

        // 5. 로그인(Jwt토큰 전달)
        response.addHeader("Authorization","Bearer "+accessToken);
        response.addHeader("Member-Name", member.getMemberName());

        return ResponseDto.success(member.getMemberName()+"님 로그인 성공");
    }
    // KakaoToken 받기
    public String getKakaoToken(String code) throws IOException{
        //Kakao에서 Token 발급 받는 URL
        URL url = new URL("https://kauth.kakao.com/oauth/token");
        // url 연결
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        // 요청 보낼 형식
        httpConn.setRequestMethod("POST");
        // 요청 파일 타입
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());

        //카카오 Client id와 프론트에서 받은 code 입력
        writer.write("grant_type=authorization_code&client_id=6d51c4942788dc0abd939ace6ee2d8b7&code="+code);
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        // response->Object
        Gson gson = new Gson();
        Map map = new HashMap();
        map = (Map) gson.fromJson(response,map.getClass());
        String accessToken=map.get("access_token").toString();
        return accessToken;
    }

    // Kakao에서 정보받기
    public JsonObject getInfo(String token) throws IOException {
        URL url = new URL("https://kapi.kakao.com/v2/user/me");
        HttpURLConnection httpConn2 = (HttpURLConnection) url.openConnection();
        // 요청 방식 입력
        httpConn2.setRequestMethod("GET");
        // 토큰 추가
        httpConn2.setRequestProperty("Authorization", "Bearer "+token);

        InputStream responseStream2 = httpConn2.getResponseCode() / 100 == 2
                ? httpConn2.getInputStream()
                : httpConn2.getErrorStream();
        Scanner sc = new Scanner(responseStream2).useDelimiter("\\A");
        String response = sc.hasNext() ? sc.next() : "";
        // response(String)->Object
        JsonParser jsonParser = new JsonParser();
        Object obj = jsonParser.parse(response);
        JsonObject info = (JsonObject) obj;
        return info;
    }

    // 유저 확인
    public boolean chekExistMember(JsonObject info){
        System.out.println(info.get("id").toString());
        if(memberRepository.existsById(Long.valueOf(info.get("id").toString()))){
            return true;
        }
        return false;
    }

    @Transactional
    public void signIn(JsonObject memberInfo){
        JsonParser jsonParser = new JsonParser();
        Object obj = jsonParser.parse(memberInfo.get("properties").toString());
        JsonObject properties = (JsonObject) obj;
        String defaultImg = "사진이 없당";
        String profileImage=defaultImg;
        if(properties.get("profile_image")!=null){
            profileImage=properties.get("profile_image").toString();
        }
        Member member = Member.builder()
                .id(Long.valueOf(memberInfo.get("id").toString()))
                .memberName(properties.get("nickname").toString())
                .profileImage(profileImage)
                .userRole(Member.Authority.ROLE_USER)
                .build();
        memberRepository.save(member);
        System.out.println(properties);
    }
}
