package ceos.springvote.dto;

import ceos.springvote.domain.Member;
import java.util.List;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private List<Member> members;

    public MemberResponseDto(List<Member> members) {
        this.members = members;
    }
}
