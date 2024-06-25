package ceos.springvote.application;

import ceos.springvote.domain.Member;
import ceos.springvote.exception.CustomException;
import ceos.springvote.exception.error.MemberErrorCode;
import ceos.springvote.jwt.domain.CustomUserDetails;
import ceos.springvote.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Member userData = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (userData != null) {

            return new CustomUserDetails(userData);
        }

        log.warn("User not found with loginId: {}", loginId);
        return null;
    }
}
