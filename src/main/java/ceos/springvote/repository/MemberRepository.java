package ceos.springvote.repository;

import ceos.springvote.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Boolean existsDistinctByLoginId(String loginId);

    public Boolean existsDistinctByEmail(String email);

    public Member findByName(String name);
    public Member findByLoginId(String loginId);
}
