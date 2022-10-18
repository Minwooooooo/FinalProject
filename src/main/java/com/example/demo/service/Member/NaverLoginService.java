package com.example.demo.service.Member;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.httpDto.responseDto.LoginResponseDto;
import com.example.demo.entity.member.Member;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NaverLoginService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;


    private final String baseUrl = "https://nid.naver.com/oauth2.0/";
    private String nidMeUrl ="https://openapi.naver.com/v1/nid/me";
    private String tokenUrlId ="token?client_id=";
    private String tokenUrlSecret = "&client_secret=";
    private String tokenUrlState="&grant_type=authorization_code&state=";
    private String tokenUrlCode ="&code=";

    @Value("${naver.client-id}")
    String ClientId;
    @Value("${naver.client-secret}")
    String ClientSecret;
    @Value("${naver.state}")
    String state ="123";

    public ResponseDto<?> naverLogin(String code, String state, HttpServletResponse response, HttpServletRequest request){
        RestTemplate restTemplate =new RestTemplate(); // 토큰을 발급 받을 URL 초기화
        HttpEntity<?> httpentity = new HttpEntity<>(new HttpHeaders()); // 토큰을 발급 받을 Http 및 Header 초기화

        //Naver에 코드를 기반으로 GET 방식으로 토큰 발급 요청
        ResponseEntity<Map> resultMap = restTemplate.exchange(makeAuthUrl(code,state), HttpMethod.GET,httpentity,Map.class);

        // Naver에서 받은 object를 String으로 변환
        String NaverAccessToken=String.valueOf(resultMap.getBody().get("access_token"));
        String NaverRefreshToken=String.valueOf(resultMap.getBody().get("refresh_token"));
        String NaverTokenType=String.valueOf(resultMap.getBody().get("token_type")); // = "Bearer"
        String NaverExpiresIn=String.valueOf(resultMap.getBody().get("expires_in")); // = "3600"

        //Naver에 토큰을 기반으로 유저 정보 요청
        Map memberInfo=nidMe(NaverAccessToken,NaverTokenType);

        // 기존 가입 여부 확인
        if(!checkExistMember(memberInfo)){
            System.out.println("신규");
            // false : 신규 회원
            // 회원가입
            signIn(memberInfo);
        }

        // 4. 로그인(Jwt토큰 생성)
        Member member = memberRepository.findByNaverId(memberInfo.get("id").toString()).get();
        member.setMemberName(memberInfo.get("name").toString());
        String accessToken = jwtTokenProvider.creatToken(member,request);

        //프로필사진 업데이트
        //changeProfileImage(memberInfo);

        // 5. 로그인(Jwt토큰 전달)
        response.addHeader("Authorization","Bearer "+accessToken);
        response.addHeader("Member-Name", member.getMemberName());

        LoginResponseDto responseDto = LoginResponseDto.builder()
                .memberId(member.getId())
                .memberName(member.getMemberName())
                .memberImg(member.getMemberImage())
                .build();

        return ResponseDto.success(responseDto);
    }

    private void signIn(Map memberInfo) {
        Member member = Member.builder()
                .id(UUID.randomUUID().toString())
                .naverId(memberInfo.get("id").toString())
                .memberName(memberInfo.get("name").toString())
                .memberImage(memberInfo.get("profile_image").toString())
                .userRole(Member.Authority.ROLE_USER)
                .build();
        memberRepository.save(member);
    }

    public Map nidMe(String NaverAccessToken,String NaverTokenType){
        RestTemplate restTemplate = new RestTemplate(); // 정보를 발급 받을 URL 초기화

        HttpHeaders headers = new HttpHeaders(); // URL에 보낼 헤더 초기화
        String nidMeHeader=NaverTokenType+" "+NaverAccessToken; // 헤더에 토큰 담기
        headers.set("Authorization",nidMeHeader);

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Map> userInfo = restTemplate.exchange(nidMeUrl,HttpMethod.GET,httpEntity, Map.class); // 정보 발급 요청
        Map userData = (Map) userInfo.getBody().get("response");
        return userData;
    }

    // 유저 기존 가입여부 확인
    public boolean checkExistMember(Map info){
        if(memberRepository.existsByNaverId(info.get("id").toString())){
            System.out.println("있음");
            return true;
        }
        System.out.println("없음");
        return false;
    }

    public String makeAuthUrl(String code,String state){
        String authUrl = baseUrl+tokenUrlId+ClientId+tokenUrlSecret+ClientSecret+tokenUrlState+state+tokenUrlCode+code;
        return authUrl;
    }
}
