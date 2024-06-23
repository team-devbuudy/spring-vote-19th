package ceos.springvote.exception.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum VoteErrorCode {
    TEAM_NOT_EXIST(HttpStatus.NOT_FOUND, "ID에 해당하는 팀이 존재하지 않습니다"),
    LEADER_NOT_EXIST(HttpStatus.NOT_FOUND, "ID에 해당하는 파트장이 존재하지 않습니다"),
    TEAM_VOTE_ALREADY_EXIST(HttpStatus.CONFLICT, "해당 팀에 이미 투표를 진행했습니다"),
    LEADER_VOTE_ALREADY_EXIST(HttpStatus.CONFLICT, "해당 파트장에 이미 투표를 진행했습니다"),
    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "ID에 해당하는 유저가 존재하지 않습니다." ),
    ILLEGAL_TEAM_VOTE(HttpStatus.BAD_REQUEST, "자신의 팀을 투표할 수 없습니다." ),
    TEAM_VOTE_NOT_EXIST(HttpStatus.NOT_FOUND, "Team에 대한 투표가 존재하지 않습니다" ),
    ILLEGAL_LEADER_VOTE(HttpStatus.BAD_REQUEST, "파트장이 아닌 사람을 투표할 수 없습니다." );
    private final HttpStatus httpStatus;
    private final String message;


    VoteErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
