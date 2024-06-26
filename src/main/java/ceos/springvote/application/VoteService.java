package ceos.springvote.application;

import ceos.springvote.domain.DemoVote;
import ceos.springvote.domain.LeaderVote;
import ceos.springvote.domain.Member;
import ceos.springvote.domain.Team;
import ceos.springvote.exception.CustomException;
import ceos.springvote.exception.error.VoteErrorCode;
import ceos.springvote.repository.DemoVoteRepository;
import ceos.springvote.repository.LeaderVoteRepository;
import ceos.springvote.repository.MemberRepository;
import ceos.springvote.repository.TeamRepository;
import java.util.List;

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
    private final DemoVoteRepository demoVoteRepository;
    private final LeaderVoteRepository leaderVoteRepository;

    public List<Member> getAllCandidates() {
        return memberRepository.findAll();
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Transactional
    public List<Team> addDemoVoteCount(Long teamId, String loginId) {
        Team target = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(VoteErrorCode.TEAM_NOT_EXIST)
        );

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new CustomException(VoteErrorCode.MEMBER_NOT_EXIST)
        );

        if(member.getTeam().equals(target)){
            throw new CustomException(VoteErrorCode.ILLEGAL_TEAM_VOTE);
        }

        target.addVoteCount();
        DemoVote vote = demoVoteRepository.findByTeamIdAndMemberId(target.getId(), member.getId());
        if(vote != null){
            vote.addVoteCount();
        }
        else{
             demoVoteRepository.save(DemoVote.builder()
                    .team(target)
                    .member(member)
                    .voteCount(1)
                    .build());
        }

        return teamRepository.findAll(Sort.by(Direction.DESC,"voteCount"));
    }

    @Transactional
    public List<Team> subDemoVoteCount(Long teamId, String loginId) {
        Team target = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(VoteErrorCode.TEAM_NOT_EXIST)
        );

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new CustomException(VoteErrorCode.MEMBER_NOT_EXIST)
        );

        DemoVote vote = demoVoteRepository.findByTeamIdAndMemberId(target.getId(), member.getId());
        if(vote != null){
            vote.subVoteCount();
            if(vote.getVoteCount() == 0){
                demoVoteRepository.delete(vote);
            }
        }
        else{
            throw new CustomException(VoteErrorCode.VOTE_NOT_EXIST);
        }

        target.subVoteCount();
        return teamRepository.findAll(Sort.by(Direction.DESC,"voteCount"));
    }

    @Transactional
    public List<Member> addLeaderVoteCount(Long memberId, String loginId) {
        Member target = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(VoteErrorCode.LEADER_NOT_EXIST)
        );

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new CustomException(VoteErrorCode.MEMBER_NOT_EXIST)
        );

        if(!target.getPart().equals(member.getPart())){
            throw new CustomException(VoteErrorCode.ILLEGAL_LEADER_VOTE);
        }

        LeaderVote vote = leaderVoteRepository.findByLeaderIdAndMemberId(target.getId(), member.getId());
        if(vote != null){
            vote.addVoteCount();
        }
        else{
            leaderVoteRepository.save(LeaderVote.builder()
                    .leader(target)
                    .member(member)
                    .voteCount(1)
                    .build());
        }

        target.addVoteCount();

        return memberRepository.findAll(Sort.by(Direction.DESC,"voteCount"));
    }

    @Transactional
    public List<Member> subLeaderVoteCount(Long memberId, String loginId) {
        Member target = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(VoteErrorCode.LEADER_NOT_EXIST)
        );

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new CustomException(VoteErrorCode.MEMBER_NOT_EXIST)
        );

        if(!target.getPart().equals(member.getPart())){
            throw new CustomException(VoteErrorCode.ILLEGAL_LEADER_VOTE);
        }

        LeaderVote vote = leaderVoteRepository.findByLeaderIdAndMemberId(target.getId(), member.getId());
        if(vote != null){
            vote.subVoteCount();
            if(vote.getVoteCount() == 0){
                leaderVoteRepository.delete(vote);
            }
        }
        else{
            throw new CustomException(VoteErrorCode.VOTE_NOT_EXIST);
        }

        target.subVoteCount();
        return memberRepository.findAll(Sort.by(Direction.DESC,"voteCount"));
    }
}
