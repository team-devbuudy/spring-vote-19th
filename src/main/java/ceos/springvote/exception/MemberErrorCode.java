package ceos.springvote.exception;

import ceos.springvote.dto.MemberErrorCodeResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode {

    ID_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 ID입니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 Email입니다."),
    INVALID_USER_INFO(HttpStatus.BAD_REQUEST, "잘못된 회원정보입니다."),
    INVALID_PASSWORD_INFO(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다.");

    private final HttpStatus httpStatus;
    private final String message;


    MemberErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public MemberErrorCodeResponse toResponse() {
        return new MemberErrorCodeResponse(this.httpStatus, this.message);
    }
}
