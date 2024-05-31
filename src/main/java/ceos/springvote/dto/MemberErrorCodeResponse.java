package ceos.springvote.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Schema(description = "MemberErrorCode의 응답 객체")
public class MemberErrorCodeResponse {
    @Schema(description = "HTTP 상태 코드", example = "409")
    private HttpStatus status;

    @Schema(description = "에러 메시지", example = "이미 존재하는 ID입니다.")
    private String message;

    public MemberErrorCodeResponse(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }
}
