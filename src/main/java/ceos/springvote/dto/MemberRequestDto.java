package ceos.springvote.dto;

import ceos.springvote.constant.Part;
import ceos.springvote.constant.Role;
import ceos.springvote.constant.Team;
import ceos.springvote.domain.Member;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MemberRequestDto(
        @NotNull(message = "이름을 입력해주세요")
        String name,

        @NotNull(message = "아이디를 입력해주세요")
        String loginId,

        @NotNull(message = "패스워드를 입력해주세요")
        String password,

        @NotNull(message = "이메일을 입력해주세요")
        String email,

        @NotNull(message = "파트를 입력해주세요")
        Part part,

        @NotNull(message = "소속 팀을 입력해주세요")
        Team team

) {
    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .part(part)
                .team(team)
                .role(Role.fromText("USER"))
                .name(name)
                .build();
    }
}
