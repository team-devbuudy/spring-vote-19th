package ceos.springvote.config;

import ceos.springvote.application.CustomUserDetailsService;
import ceos.springvote.jwt.filter.JwtFilter;
import ceos.springvote.jwt.filter.LoginFilter;
import ceos.springvote.jwt.util.JwtUtil;
import ceos.springvote.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(Collections.singletonList("*")); //모든 종류의 HTTP 헤더를 허용하도록 설정
        config.setAllowedMethods(Collections.singletonList("*")); //모든 종류의 HTTP 메소드를 허용하도록 설정
        config.setAllowedOriginPatterns(
                Arrays.asList(
                        "http://localhost:3000",
                        "http://43.201.123.205:3000",
                        "https://react-vote-19th-two.vercel.app")); // 허용할 origin 추가
        config.setAllowCredentials(true); //인증 정보와 관련된 요청을 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService customService() {
        return new CustomUserDetailsService(memberRepository); // 빈으로 등록된 UserDetailsService
    }

    @Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customService());
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        JwtFilter jwtFilter = new JwtFilter(jwtUtil);
        LoginFilter loginFilter = new LoginFilter(authenticationManager(), jwtUtil, memberRepository);

        loginFilter.setFilterProcessesUrl("/login");

        // CORS 설정 적용
        http.cors(customizer -> customizer.configurationSource(corsConfigurationSource()));

        http.
                csrf((auth) -> auth.disable());
        http.
                formLogin((auth) -> auth.disable());
        http.
                httpBasic((auth) -> auth.disable());
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/members/join", "/swagger-ui/**", "/v3/api-docs/**",
                                "/swagger-ui.html", "/webjars/**").permitAll()
                        .requestMatchers("/votes").hasRole("USER")
                        .anyRequest().authenticated());

        http
                .addFilterBefore(jwtFilter, LoginFilter.class);
        http
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


}
