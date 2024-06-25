package ceos.springvote.dto;

import ceos.springvote.constant.Part;
import ceos.springvote.constant.Role;
import ceos.springvote.domain.Member;
import ceos.springvote.domain.Team;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberRequestDto{
    @NotNull(message = "이름을 입력해주세요")
    String name;

    @NotNull(message = "아이디를 입력해주세요")
    String loginId;

    @NotNull(message = "패스워드를 입력해주세요")
    String password;

    @NotNull(message = "이메일을 입력해주세요")
    String email;

    @NotNull(message = "파트를 선택해주세요")
    Part part;

    @NotNull(message = "소속 팀을 선택해주세요")
    Long teamId;

    public Member toEntity(Team targetTeam, String pwd) {
        return Member.builder()
                .loginId(loginId)
                .password(pwd)
                .email(email)
                .part(part)
                .team(targetTeam)
                .role(Role.fromText("USER"))
                .name(name)
                .build();
    }
}
