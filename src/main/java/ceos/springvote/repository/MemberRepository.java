package ceos.springvote.repository;

import ceos.springvote.domain.Member;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Boolean existsDistinctByLoginId(String loginId);

    public Boolean existsDistinctByEmail(String email);

    public Member findByName(String name);
    public Member findByLoginId(String loginId);

    public List<Member> findAllByIsLeaderTrue();

    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.team WHERE m.loginId = :loginId")
    Member findByLoginIdWithTeam(@Param("loginId") String loginId);
}
