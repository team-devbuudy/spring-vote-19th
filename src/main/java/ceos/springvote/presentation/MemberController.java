package ceos.springvote.presentation;

import ceos.springvote.application.MemberService;
import ceos.springvote.dto.MemberRequestDto;
import ceos.springvote.exception.CustomException;
import ceos.springvote.exception.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@Tag(name = "Member Controller", description = "멤버 컨트롤러")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    @Operation(summary = "회원가입 로직", description = "ID와 PASSWORD를 입력해 회원가입을 진행한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공 시 고유 ID를 반환합니다",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))
            }),
            @ApiResponse(responseCode = "409", description = "아이디 혹은 이메일이 중복이면 에러 정보를 반환합니다",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseTemplate.class))
            })
    })
    public ResponseEntity<Long> join(@RequestBody MemberRequestDto request) throws CustomException{
        System.out.println(request.getLoginId());
        return ResponseEntity.ok(memberService.join(request));
    }
}
