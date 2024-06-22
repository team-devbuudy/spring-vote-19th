package ceos.springvote.presentation;

import ceos.springvote.application.VoteService;
import ceos.springvote.domain.Team;
import ceos.springvote.dto.MemberResponseDto;
import ceos.springvote.dto.VoteResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votes")
@Tag(name = "Vote Controller", description = "투표 관련 컨트롤러")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @GetMapping("/candidates")
    @Operation(summary = "후보 목록 조회", description = "투표할 수 있는 후보자 목록을 조회합니다 (일단 회원가입한 사람들은 전부 후보가 되도록 함)")
    public ResponseEntity<MemberResponseDto> candidatesList() {
        return ResponseEntity.ok(voteService.getAllCandidates());
    }

    @GetMapping("/teams")
    @Operation(summary = "팀 목록 조회", description = "투표할 수 있는 팀 목록을 조회합니다")
    public ResponseEntity<List<Team>> teamList() {
        return ResponseEntity.ok(voteService.getAllTeams());
    }


    @PostMapping("/demo/{teamId}")
    @Operation(summary = "데모데이 투표", description = "데모데이 투표수를 +1")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투표 성공 시 성공 메시지를 반환합니다"),
            @ApiResponse(responseCode = "404", description = "팀이 존재하지 않을 경우 에러 메시지를 반환합니다"),
            @ApiResponse(responseCode = "409", description = "이미 투표를 진행했을 경우 에러 메시지를 반환합니다")
    })
    public ResponseEntity<VoteResponseDto> voteDemo(@PathVariable Long teamId
                                                    /*, @AuthenticationPrincipal UserDetails currentUser*/) {
        return ResponseEntity.ok(voteService.addDemoVoteCount(teamId/*, currentUser.getUser()*/));
    }

    @DeleteMapping("/demo/{teamId}")
    @Operation(summary = "데모데이 투표 취소", description = "데모데이 투표수를 -1")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투표 취소 성공 시 성공 메시지를 반환합니다"),
            @ApiResponse(responseCode = "404", description = "팀 혹은 이전 투표가 존재하지 않을 경우 에러 메시지를 반환합니다")
    })
    public ResponseEntity<VoteResponseDto> cancelVoteDemo(@PathVariable Long teamId
                                                          /*, @AuthenticationPrincipal UserDetails currentUser*/) {
        return ResponseEntity.ok(voteService.subDemoVoteCount(teamId /*,currentUser.getUser()*/));
    }

    @PostMapping("/leader/{memberId}")
    @Operation(summary = "파트장 투표", description = "파트장 투표수를 +1")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투표 성공 시 성공 메시지를 반환합니다"),
            @ApiResponse(responseCode = "404", description = "후보자가 존재하지 않을 경우 에러 메시지를 반환합니다"),
            @ApiResponse(responseCode = "409", description = "이미 투표를 진행했을 경우 에러 메시지를 반환합니다")
    })
    public ResponseEntity<VoteResponseDto> voteLeader(@PathVariable Long memberId
                                                      /*, @AuthenticationPrincipal UserDetails currentUser*/) {
        return ResponseEntity.ok(voteService.addLeaderVoteCount(memberId/*, currentUser.getUser()*/));
    }

    @DeleteMapping("/leader/{memberId}")
    @Operation(summary = "파트장 투표 취소", description = "파트장 투표수를 -1")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투표 성공 시 성공 메시지를 반환합니다"),
            @ApiResponse(responseCode = "404", description = "후보자가 존재하지 않을 경우 에러 메시지를 반환합니다")
    })
    public ResponseEntity<VoteResponseDto> cancelVoteLeader(@PathVariable Long memberId
                                                            /*, @AuthenticationPrincipal UserDetails currentUser*/) {
        return ResponseEntity.ok(voteService.subLeaderVoteCount(memberId/*, currentUser.getUser()*/));
    }

}
