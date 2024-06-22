package ceos.springvote.application;

import ceos.springvote.domain.Member;
import ceos.springvote.jwt.domain.CustomUserDetails;
import ceos.springvote.repository.MemberRepository;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Member userData = memberRepository.findByLoginId(loginId);

        if (userData != null) {

            return new CustomUserDetails(userData);
        }

        return null;
    }
}
