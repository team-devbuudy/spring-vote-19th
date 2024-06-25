package ceos.springvote.repository;

import ceos.springvote.domain.DemoVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoVoteRepository extends JpaRepository<DemoVote, Long>{
    DemoVote findByTeamIdAndMemberId(Long teamId, Long memberId);
}
