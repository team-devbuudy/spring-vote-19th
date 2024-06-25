package ceos.springvote.repository;

import ceos.springvote.domain.LeaderVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderVoteRepository extends JpaRepository<LeaderVote, Long>{
    LeaderVote findByLeaderIdAndMemberId(Long LeaderId, Long memberId);
}
