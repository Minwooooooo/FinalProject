package com.example.demo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtCustomFilter extends OncePerRequestFilter {
    //OncePerRequestFilter :
    //GenericFilterBean :

    // jwt토큰을 가져와 유효성 검사 진행후 유효하다면 토큰에 담긴 정보(유저 권한 등)를 SecurityContext에 저장

    public static String AUTHORIZATION_HEADER = "Authorization";
    public static String TOKEN_TYPE = "Bearer ";
    private static final Logger logger= LoggerFactory.getLogger(JwtCustomFilter.class);
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        // 헤더에서 토큰 꺼내기
        String token = getToken(request);
        // System.out.println("꺼내온 토큰 : "+token);

        // 토큰 존재여부 && 토큰 유효 여부 확인
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)==null) {
            //System.out.println("토큰 존재여부 && 토큰 유효 여부 확인");
            Claims claims;
            try {
                claims= jwtTokenProvider.tempClaim(token);
            } catch (ExpiredJwtException e){
                claims=e.getClaims();
            }

            //토큰에서 정보 확인
            Authentication userAuth = jwtTokenProvider.getAuthentication(token);

            // 저장전 SecurityContextHolder에 담긴 권한
            Authentication temp_auth1=SecurityContextHolder.getContext().getAuthentication();
            //토큰의 유저 권한을 SecurityContextHolder에 저장
            SecurityContextHolder.getContext().setAuthentication(userAuth);
            // 저장 후 SecurityContextHolder에 담긴 권한
            Authentication temp_auth2=SecurityContextHolder.getContext().getAuthentication();

            System.out.println(temp_auth1+" --필터적용-> "+temp_auth2);

            logger.debug("SecurityContextHolder에 '{}' 인증정보를 저장했습니다, uri: {}",userAuth.getName(),request.getRequestURI());
        }
        else {
            logger.debug("JWT토큰이 유효하지 않습니다.");
        }

        filterChain.doFilter(request,response);
    }

    private String getToken(HttpServletRequest request) {
        String accessToken = request.getHeader(AUTHORIZATION_HEADER);
        //System.out.println("Filter에서 받은 토큰 : "+accessToken);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(TOKEN_TYPE)) {
            return accessToken.substring(7);
        }
        return null;
    }

}
