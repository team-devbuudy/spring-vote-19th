package ceos.springvote.application;

import ceos.springvote.domain.Member;
import ceos.springvote.domain.Team;
import ceos.springvote.dto.MemberRequestDto;
import ceos.springvote.exception.error.MemberErrorCode;
import ceos.springvote.exception.CustomException;
import ceos.springvote.jwt.domain.CustomUserDetails;
import ceos.springvote.repository.MemberRepository;
import ceos.springvote.repository.TeamRepository;
import java.beans.Encoder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long join(@Valid MemberRequestDto request) throws CustomException {

        Team targetTeam = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new CustomException(MemberErrorCode.TEAM_NOT_EXIST));
        Member member = request.toEntity(targetTeam, bCryptPasswordEncoder.encode(request.getPassword()));
        isDuplicate(member);
        return memberRepository.save(member).getId();
    }

    private void isDuplicate(Member member) throws CustomException {
        if (memberRepository.existsDistinctByLoginId(member.getLoginId())) {
            throw new CustomException(MemberErrorCode.ID_ALREADY_EXIST);
        }

        if (memberRepository.existsDistinctByEmail(member.getEmail())) {
            throw new CustomException(MemberErrorCode.EMAIL_ALREADY_EXIST);
        }
    }

}
