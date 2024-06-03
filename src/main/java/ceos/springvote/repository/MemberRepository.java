package ceos.springvote.repository;

import ceos.springvote.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Boolean existsDistinctByLoginId(String loginId);

    public Boolean existsDistinctByEmail(String email);
}
