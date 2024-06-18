package ceos.springvote.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VoteResponseDto {
    String message;
    int currentVoteCount;
}
