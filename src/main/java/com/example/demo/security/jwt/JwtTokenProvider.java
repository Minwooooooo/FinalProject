package com.example.demo.security.jwt;


import com.example.demo.entity.member.Member;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key JWT_KEY;

    //key 설정
    public JwtTokenProvider(@Value("${jwt.key}")String secretKey){
        byte[] key= Decoders.BASE64.decode(secretKey);
        this.JWT_KEY= Keys.hmacShaKeyFor(key);
    }


    // Token 생성
    // subject : Kakao에서 받은 meberId (unique)
    // claim : 이름/권한
    public String creatToken(Member member){
        System.out.println(member.getMemberName());
        System.out.println(member.getId());
        return Jwts.builder()
                .setSubject(member.getId().toString())
                .claim("name",member.getMemberName())
                .claim("auth",member.getUserRole().toString())
                .signWith(JWT_KEY,SignatureAlgorithm.HS256)
                .compact();
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

    // 토큰 복호화(+NoBaerer)
    public Claims tempClaimNoBaerer(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JWT_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }


    // Request에서 토큰 가져오기
    public String getToken(HttpServletRequest request) {
        String accessToken = request.getHeader(JwtCustomFilter.AUTHORIZATION_HEADER);
        //System.out.println("Filter에서 받은 토큰 : "+accessToken);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(JwtCustomFilter.TOKEN_TYPE)) {
            return accessToken.substring(7);
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
        String returnMsg;
        try{
            tempClaim(token);
            return null;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            returnMsg="Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.";
            return returnMsg;
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
            returnMsg="Expired JWT token, 만료된 JWT token 입니다.";
            return returnMsg;
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            returnMsg="Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.";
            return returnMsg;
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, JWT 토큰이 존재하지 않습니다");
            returnMsg="JWT claims is empty, JWT 토큰이 존재하지 않습니다";
            return returnMsg;
        } catch (Exception e) {
            log.info("JWT claims is wrong, 잘못된 JWT 토큰 입니다.");
            returnMsg="JWT claims is wrong, 잘못된 JWT 토큰 입니다.";
            return returnMsg;
        }
    }

}
