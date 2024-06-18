package ceos.springvote.repository;

import ceos.springvote.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    public Vote findDemoVoteByMember_Id(Long memberId);

    public Vote findLeaderVoteByMember_Id(Long memberId);
}
