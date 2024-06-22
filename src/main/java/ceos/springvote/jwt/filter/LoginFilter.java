package ceos.springvote.jwt.filter;

import ceos.springvote.jwt.util.JwtUtil;
import ceos.springvote.jwt.domain.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 파서를 위한 ObjectMapper

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        //client 요청에서 loginId, password 추출
        String loginId = obtainLoginId(request);
        String password = obtainPassword(request);

        //spring security에서 loginId와 password 검증하려면 token(DTO)에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginId, password,
                null);


        //authToken 검증을 위해 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    //Login 성공시 실행하는 메소드 (JWT 발급 과정)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String loginId = userDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        String token = jwtUtil.createJwt(loginId, role, 1000 * 60 * 60L);

        response.addHeader("Access", "Bearer "+token);
        response.setStatus(HttpStatus.OK.value());
    }


    //Login 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException exception) {
        response.setStatus(401);
    }

    // loginId를 요청에서 추출하는 메서드 추가
    protected String obtainLoginId(HttpServletRequest request) {
        return request.getParameter("loginId"); // request 파라미터에서 loginId를 추출
    }

}
