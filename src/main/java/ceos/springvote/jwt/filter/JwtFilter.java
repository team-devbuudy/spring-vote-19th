package ceos.springvote.jwt.filter;

import ceos.springvote.constant.Role;
import ceos.springvote.domain.Member;
import ceos.springvote.jwt.domain.CustomUserDetails;
import ceos.springvote.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Getter
public class JwtFilter extends OncePerRequestFilter { //스프링 시큐리티 filter chain에 요청에 담긴 JWT를 검증하기 위한 커스텀 필터
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // request에서 Access 헤더를 추출
        String authorization = request.getHeader("Access");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("Token is null");
            filterChain.doFilter(request, response);

            return; //토큰이 없다면 종료
        }

        System.out.println(authorization);
        String token = authorization.substring("Bearer ".length()); //Bearer 부분 제거 후 순수 토큰만 추출
        
        //토큰 만료여부 확인
        if (jwtUtil.isExpired(token)) {

            System.out.println("Token is expired");
            filterChain.doFilter(request, response);
            
            return; //토큰이 만료되었다면 종료
        }

        // token에서 loginId, role을 획득
        String loginId = jwtUtil.getLoginId(token);
        String role = jwtUtil.getRole(token);


        Member member = Member.builder()
                .loginId(loginId)
                .role(Role.fromText(role))
                .build();

        //UserDetails에 member 정보 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        //Spring security 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //Session에 유저 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
