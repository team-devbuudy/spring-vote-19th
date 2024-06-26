package ceos.springvote.domain;

import ceos.springvote.constant.Part;
import ceos.springvote.constant.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @JsonIgnore
    private String loginId;

    @JsonIgnore
    private String password;

    @Email
    @JsonIgnore
    private String email;

    private Part part;

    @JsonIgnore
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;

    @Builder.Default
    private Integer voteCount = 0;

    public void addVoteCount() {
        this.voteCount++;
    }

    public void subVoteCount() {
        this.voteCount--;
    }
}
