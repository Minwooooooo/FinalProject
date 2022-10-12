package com.example.demo.service.Member;

import com.example.demo.dto.httpDto.responseDto.LoginResponseDto;
import com.example.demo.entity.member.Member;
import com.example.demo.repository.member.MemberRepository;


import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.dto.ResponseDto;
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

        //프로필사진 업데이트
        changeProfileImage(memberInfo);

        // 5. 로그인(Jwt토큰 전달)
        response.addHeader("Authorization","Bearer "+accessToken);
        response.addHeader("Member-Name", member.getMemberName());

        LoginResponseDto responseDto = LoginResponseDto.builder()
                .memberId(member.getId())
                .memberName(member.getMemberName())
                .memberImg(member.getProfileImage())
                .build();

        return ResponseDto.success(responseDto);
    }
    // KakaoToken 받기
    // Kakao developer의 예제를 기반으로 CURL to JAVA 컨버터를 통해서 작성
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

        // response->Object
        // getKakaoToken()과 같이 Gson을 사용시 숫자를 자동으로 Int형으로 받는다
        // 그런데 Kakao ID는 10자리 숫자로 Long이 아닌 Int형으로 받을 시 오류가 발생
        // -> Gson 라이브러리 내 JsonParser를 이용하여 해결
        JsonParser jsonParser = new JsonParser();
        Object obj = jsonParser.parse(response);
        JsonObject info = (JsonObject) obj;
        return info;
    }

    // 유저 기존 가입여부 확인
    public boolean chekExistMember(JsonObject info){
        if(memberRepository.existsById(Long.valueOf(info.get("id").toString()))){
            return true;
        }
        return false;
    }


    // 회원가입 (신규유저만 해당)
    @Transactional
    public void signIn(JsonObject memberInfo){
        // Kakao의 경우 이중 객체형태의 정보를 제공하므로 key값이 properties인 Value를 객체로 변환
        JsonParser jsonParser = new JsonParser();
        Object obj = jsonParser.parse(memberInfo.get("properties").toString());
        JsonObject properties = (JsonObject) obj;


        // 프로필 사진이 없을시 들어오는 URL 체크 필요
        String defaultImg = "사진이 없당";
        String profileImage=defaultImg;
        if(properties.get("profile_image")!=null){
            String temp_profileImage=properties.get("profile_image").toString();
            profileImage=temp_profileImage.substring(1,temp_profileImage.length()-1);
        }
        // 이름이랑 이메일이 ""로 묶이는 오류 수정
        String temp_name=properties.get("nickname").toString();

        // 신규 회원가입 빌드
        // 권한은 기본값인 USER
        Member member = Member.builder()
                .id(Long.valueOf(memberInfo.get("id").toString()))
                .memberName(temp_name.substring(1,temp_name.length()-1))
                .profileImage(profileImage)
                .userRole(Member.Authority.ROLE_USER)
                .build();
        memberRepository.save(member);
    }


    //프로필사진 변경
    @Transactional
    public void changeProfileImage(JsonObject memberInfo){
        // Kakao의 경우 이중 객체형태의 정보를 제공하므로 key값이 properties인 Value를 객체로 변환
        JsonParser jsonParser = new JsonParser();
        Object obj = jsonParser.parse(memberInfo.get("properties").toString());
        JsonObject properties = (JsonObject) obj;


        // 프로필 사진이 없을시 들어오는 URL 체크 필요
        String defaultImg = "null";
        String profileImage=defaultImg;
        if(properties.get("profile_image")!=null){
            String temp_profileImage=properties.get("profile_image").toString();
            profileImage=temp_profileImage.substring(1,temp_profileImage.length()-1);
        }

        Member member = memberRepository.findById(Long.valueOf(memberInfo.get("id").toString())).get();

        if(!member.getProfileImage().equals(profileImage)){
            member.setNewProfileImage(profileImage);
        }

    }

}
