package com.numble.reservationsystem.config;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    final String SECRET_KEY;
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 토큰 꺼내기
        String accessToken = resolveToken(request,"access");
        String refreshToken = resolveToken(request, "refresh");


        // 엑세스 토큰이 없다면 익셉션 발생 -> 에러 처리
        if (accessToken == null) {
            chain.doFilter(request, response);
            return;
        }

        int accessToken_status = jwtProvider.validateToken(accessToken, SECRET_KEY);
        // 엑세스 토큰 및 리프레쉬 토큰 검증
        if (accessToken_status == 1) {
            String email = jwtProvider.getEmailFromToken(accessToken,SECRET_KEY);
            setAuthentication(email, request);
        } else if (accessToken_status == 0 && refreshToken != null) {
            int refreshToken_status = jwtProvider.validateToken(refreshToken, SECRET_KEY);
            // 액세스 토큰이 만려되면 리프레쉬 토큰으로 새로운 액세스 토큰 발급
            if (refreshToken_status == 1) {
                String email = jwtProvider.getEmailFromToken(refreshToken,SECRET_KEY);
                String newAccessToken = jwtProvider.createAccessToken(email, SECRET_KEY);
                jwtProvider.setHeaderAccessToken(response,newAccessToken);
                setAuthentication(jwtProvider.getEmailFromToken(newAccessToken,SECRET_KEY),request);
            }
            // 리프레쉬 토큰이 만료되었다면 익셉션 발생
            else if(refreshToken_status == 0) {
                response.setStatus(401);
                response.setHeader("code","Refresh_Token_expired");
            }
            else {
                response.setStatus(403);
                response.setHeader("code","Mistaken_Refresh_Token");
            }
        }
        else {
            response.setStatus(403);
            response.setHeader("code","Mistaken_Access_Token");
        }
        chain.doFilter(request, response);
    }


    // USER 권한 authentication 에 저장
    public void setAuthentication(String email, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        email,
                        "",
                        List.of(new SimpleGrantedAuthority("USER")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }


    // 요청에 담긴 헤더에서 토큰값 가져오기
    // 토큰의 타입에 따라 다르게 가져올 수 있음.
    private String resolveToken(HttpServletRequest request, String tokenType) {
        String headerAuth = tokenType.equals("access")
                ? request.getHeader("access")
                : request.getHeader("refresh");
        if (StringUtils.hasText(headerAuth))
            return headerAuth;
        return null;
    }

}