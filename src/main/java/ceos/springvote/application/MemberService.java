package ceos.springvote.application;

import ceos.springvote.domain.Member;
import ceos.springvote.dto.MemberRequestDto;
import ceos.springvote.exception.MemberErrorCode;
import ceos.springvote.exception.MemberException;
import ceos.springvote.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(MemberRequestDto request) throws MemberException {
        Member member = request.toEntity();
        isDuplicate(member);
        return memberRepository.save(member).getId();
    }

    private void isDuplicate(Member member) throws MemberException{
        if (memberRepository.existsDistinctByLoginId(member.getLoginId())) {
            throw new MemberException(MemberErrorCode.ID_ALREADY_EXIST);
        }

        if (memberRepository.existsDistinctByEmail(member.getEmail())) {
            throw new MemberException(MemberErrorCode.EMAIL_ALREADY_EXIST);
        }
    }
}
