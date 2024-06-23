package ceos.springvote.application;

import ceos.springvote.domain.Member;
import ceos.springvote.domain.Team;
import ceos.springvote.dto.MemberResponseDto;
import ceos.springvote.dto.VoteResponseDto;
import ceos.springvote.exception.CustomException;
import ceos.springvote.exception.error.VoteErrorCode;
import ceos.springvote.repository.MemberRepository;
import ceos.springvote.repository.TeamRepository;
import ceos.springvote.repository.VoteRepository;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class VoteService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final VoteRepository voteRepository;

    public MemberResponseDto getAllCandidates() {
        List<Member> result = memberRepository.findAll();
        return new MemberResponseDto(result);

    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Transactional
    public VoteResponseDto addDemoVoteCount(Long teamId) {
        Team target = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(VoteErrorCode.TEAM_NOT_EXIST)
        );
        target.addVoteCount();
        return VoteResponseDto.builder()
                .currentVoteCount(target.getVoteCount())
                .message("데모데이 투표가 완료되었습니다")
                .build();
    }

    @Transactional
    public VoteResponseDto subDemoVoteCount(Long teamId) {
        Team target = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(VoteErrorCode.TEAM_NOT_EXIST)
        );

        Optional<Integer> result = target.subVoteCount();
        result.orElseThrow(() -> new CustomException(VoteErrorCode.VOTECOUNT_ALREADY_ZERO));

        return VoteResponseDto.builder()
                .currentVoteCount(target.getVoteCount())
                .message("데모데이 투표 취소가 완료되었습니다")
                .build();
    }

    @Transactional
    public VoteResponseDto addLeaderVoteCount(Long memberId) {
        Member target = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(VoteErrorCode.LEADER_NOT_EXIST)
        );
        target.addVoteCount();
        return VoteResponseDto.builder()
                .currentVoteCount(target.getVoteCount())
                .message("파트장 투표가 완료되었습니다")
                .build();
    }

    @Transactional
    public VoteResponseDto subLeaderVoteCount(Long memberId) {
        Member target = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(VoteErrorCode.LEADER_NOT_EXIST)
        );
        Optional<Integer> result = target.subVoteCount();
        result.orElseThrow(() -> new CustomException(VoteErrorCode.VOTECOUNT_ALREADY_ZERO));

        return VoteResponseDto.builder()
                .currentVoteCount(target.getVoteCount())
                .message("파트장 투표 취소가 완료되었습니다")
                .build();
    }
}
