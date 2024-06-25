package ceos.springvote.application;

import ceos.springvote.domain.Member;
import ceos.springvote.domain.Team;
import ceos.springvote.dto.VoteResponseDto;
import ceos.springvote.exception.CustomException;
import ceos.springvote.exception.error.VoteErrorCode;
import ceos.springvote.repository.MemberRepository;
import ceos.springvote.repository.TeamRepository;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class VoteService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    public List<Member> getAllCandidates() {
        return memberRepository.findAll(Sort.by(Direction.DESC, "voteCount"));
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll(Sort.by(Direction.DESC, "voteCount"));
    }

    @Transactional
    public VoteResponseDto addDemoVoteCount(Long teamId, Long id) {
        Team target = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(VoteErrorCode.TEAM_NOT_EXIST)
        );

        Member member = memberRepository.findById(id).orElseThrow(
                () -> new CustomException(VoteErrorCode.MEMBER_NOT_EXIST)
        );

        if(member.getTeam().equals(target)){
            throw new CustomException(VoteErrorCode.ILLEGAL_TEAM_VOTE);
        }

        if(member.getIsEnableVoteTeam().equals(Boolean.FALSE)){
            throw new CustomException(VoteErrorCode.TEAM_VOTE_ALREADY_EXIST);
        }

        target.addVoteCount();
        member.updateDemoVoteAble();

        return teamRepository.findAll(Sort.by(Direction.DESC,"voteCount"));
    }

    @Transactional
    public VoteResponseDto subDemoVoteCount(Long teamId, Long id) {
        Team target = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(VoteErrorCode.TEAM_NOT_EXIST)
        );

        Member member = memberRepository.findById(id).orElseThrow(
                () -> new CustomException(VoteErrorCode.MEMBER_NOT_EXIST)
        );

        if(member.getIsEnableVoteTeam().equals(Boolean.TRUE)){
            throw new CustomException(VoteErrorCode.TEAM_VOTE_NOT_EXIST);
        }

        target.subVoteCount();
        member.updateDemoVoteAble();
        return teamRepository.findAll(Sort.by(Direction.DESC,"voteCount"));
    }

    @Transactional
    public VoteResponseDto addLeaderVoteCount(Long memberId, Long id) {
        Member target = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(VoteErrorCode.LEADER_NOT_EXIST)
        );

        Member member = memberRepository.findById(id).orElseThrow(
                () -> new CustomException(VoteErrorCode.MEMBER_NOT_EXIST)
        );

        if(!target.getPart().equals(member.getPart())){
            throw new CustomException(VoteErrorCode.ILLEGAL_LEADER_VOTE);
        }

        if(target.getIsLeader().equals(Boolean.FALSE)){
            throw new CustomException(VoteErrorCode.ILLEGAL_LEADER_VOTE);
        }

        if(member.getIsEnableVoteLeader().equals(Boolean.FALSE)){
            throw new CustomException(VoteErrorCode.LEADER_VOTE_ALREADY_EXIST);
        }

        target.addVoteCount();
        member.updateLeaderVoteAble();
        return memberRepository.findAll(Sort.by(Direction.DESC,"voteCount"))
                .stream()
                .filter(m -> m.getIsLeader().equals(Boolean.TRUE))
                .toList();
    }

    @Transactional
    public VoteResponseDto subLeaderVoteCount(Long memberId, Long id) {
        Member target = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(VoteErrorCode.LEADER_NOT_EXIST)
        );

        Member member = memberRepository.findById(id).orElseThrow(
                () -> new CustomException(VoteErrorCode.MEMBER_NOT_EXIST)
        );

        if(!target.getPart().equals(member.getPart())){
            throw new CustomException(VoteErrorCode.ILLEGAL_LEADER_VOTE);
        }

        if(target.getIsLeader().equals(Boolean.FALSE)){
            throw new CustomException(VoteErrorCode.ILLEGAL_LEADER_VOTE);
        }

        if(member.getIsEnableVoteLeader().equals(Boolean.FALSE)){
            throw new CustomException(VoteErrorCode.LEADER_VOTE_ALREADY_EXIST);
        }
        target.subVoteCount();
        target.updateLeaderVoteAble();
        return memberRepository.findAll(Sort.by(Direction.DESC,"voteCount"))
                .stream()
                .filter(m -> m.getIsLeader().equals(Boolean.TRUE))
                .toList();
    }
}
