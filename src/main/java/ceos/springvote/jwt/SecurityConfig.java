package ceos.springvote.jwt;

import ceos.springvote.application.CustomUserDetailsService;
import ceos.springvote.jwt.filter.JwtFilter;
import ceos.springvote.jwt.filter.LoginFilter;
import ceos.springvote.jwt.util.JwtUtil;
import ceos.springvote.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }

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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        JwtFilter jwtFilter = new JwtFilter(jwtUtil);
        LoginFilter loginFilter = new LoginFilter(authenticationManager(), jwtUtil);

        loginFilter.setFilterProcessesUrl("/login");
        http.
                csrf((auth)-> auth.disable());
        http.
                formLogin((auth)->auth.disable());
        http.
                httpBasic((auth)->auth.disable());
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/members/join").permitAll()
                        .requestMatchers("/vote").hasRole("USER")
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
