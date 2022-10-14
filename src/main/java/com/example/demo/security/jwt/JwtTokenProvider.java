package com.example.demo.security.jwt;


import com.example.demo.entity.member.Member;
import com.example.demo.entity.member.RefreshToken;
import com.example.demo.repository.member.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key JWT_KEY;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24; //1일
    private static final long Refresh_TOKEN_EXPIRE_TIME = ACCESS_TOKEN_EXPIRE_TIME*7; //7일
    private final RefreshTokenRepository refreshTokenRepository;

    //key 설정
    public JwtTokenProvider(@Value("${jwt.key}")String secretKey, RefreshTokenRepository refreshTokenRepository){
        this.refreshTokenRepository = refreshTokenRepository;
        byte[] key= Decoders.BASE64.decode(secretKey);
        this.JWT_KEY= Keys.hmacShaKeyFor(key);
    }


    // Token 생성
    // subject : Kakao에서 받은 meberId (unique)
    // claim : 이름/권한
    public String creatToken(Member member, HttpServletRequest request){
        long date = new Date().getTime();

        // MemberIP확인
        String memberIP=getMemberIP(request);
        System.out.println(memberIP);

        // AccessToken생성
        String accessToken=Jwts.builder()
                .setSubject(member.getId().toString())
                .claim("name",member.getMemberName())
                .claim("auth",member.getUserRole().toString())
                .setExpiration(new Date(date+ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(JWT_KEY,SignatureAlgorithm.HS256)
                .compact();

        // RefreshToken생성
        String refreshToken=Jwts.builder()
                .setExpiration(new Date(date+Refresh_TOKEN_EXPIRE_TIME))
                .signWith(JWT_KEY,SignatureAlgorithm.HS256)
                .compact();

        // RefreshToken Entity 생성
        RefreshToken newRefreshToken= RefreshToken.builder()
                .refreshTokenId(UUID.randomUUID().toString())
                .refreshToken(refreshToken)
                .memberAddress(memberIP)
                .status(0)
                .build();


        return accessToken;
    }

    // 스프링 시큐리티에 저장된 권한 불러오기
    public Authentication getAuthentication(String token){
        Claims claims=tempClaim(token);
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        User principal = new User(claims.getSubject(),"",authorities);

        return new UsernamePasswordAuthenticationToken(principal,null,authorities);
    }

    // HttpServletRequest 검증 ->토큰 꺼내기
    public String validateRequest(HttpServletRequest request){
        String validation=null;
        //AccessToken 불러오기
        String accessToken=request.getHeader(JwtCustomFilter.AUTHORIZATION_HEADER);

        //RefreshToken 불러오기
        String refreshToken=request.getHeader(JwtCustomFilter.REFRESHTOKEN_HEADER);

        // 토큰 유효성 검증
        String accessTokenvalidate=validateToken(accessToken);
        String refreshTokenvalidate=validateToken(refreshToken);

        // 유효성 검증 결과 체크
        if(accessTokenvalidate!=null){
            validation=accessTokenvalidate;
            return validation;
        }
        if(refreshTokenvalidate!=null){
            validation=refreshTokenvalidate;
            return validation;
        }

        // RefreshToken DB확인
        Optional<RefreshToken> temp_refreshTokenInDb=refreshTokenRepository.findById(refreshToken);
        if(temp_refreshTokenInDb.isEmpty()){
            return ("없는 Rtoken");
        }

        RefreshToken refreshTokenInDb = temp_refreshTokenInDb.get();
        // IP 확인
        if(!request.getRemoteAddr().equals(refreshTokenInDb.getMemberAddress())){
            return ("IP error");
        }

        return validation;
    }


    // 토큰 복호화(+Baerer)
    public Claims tempClaim(String token){
        String temp_token=token.substring(7);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JWT_KEY)
                .build()
                .parseClaimsJws(temp_token)
                .getBody();
        return claims;
    }
    // Request에서 토큰 가져오기
    public String getToken(HttpServletRequest request) {
        String accessToken = request.getHeader(JwtCustomFilter.AUTHORIZATION_HEADER);
        //System.out.println("Filter에서 받은 토큰 : "+accessToken);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(JwtCustomFilter.TOKEN_TYPE)) {
            return accessToken;
        }
        return null;
    }

    // Token에서 이름 가져오기
    public String getMemberNameByToken(String token){
        String memberName=tempClaim(token).get("name").toString();
        return memberName;
    }

    // Token에서 Id 가져오기
    public Long getMemberIdByToken(String token){
        Long memberId=Long.valueOf(tempClaim(token).getSubject());
        return memberId;
    }



    // 토큰 유효성 검증
    public String validateToken(String token) {
        String returnMsg = null;
        try{
            tempClaim(token);
            return null;
        }
        catch (SecurityException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            returnMsg="Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.";
            return returnMsg;
        }
        catch (MalformedJwtException e) {
            log.info("Malformed JWT token, 잘못된 JWT 형식 입니다.");
            returnMsg="Malformed JWT token, 잘못된 JWT 형식 입니다.";
            return returnMsg;
        }
        catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
            returnMsg="Expired JWT token, 만료된 JWT token 입니다.";
            return returnMsg;
        }
        catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            returnMsg="Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.";
            return returnMsg;
        }
        catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, JWT 토큰이 존재하지 않습니다");
            returnMsg="JWT claims is empty, JWT 토큰이 존재하지 않습니다";
            return returnMsg;
        }
        catch (Exception e) {
            log.info("JWT claims is wrong, 잘못된 JWT 토큰 입니다.");
            returnMsg="JWT claims is wrong, 잘못된 JWT 토큰 입니다.";
            return returnMsg;
        }
        finally {
            System.out.println(returnMsg);
        }
    }

    private String getMemberIP(HttpServletRequest request){
        String ip = null;

        ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
