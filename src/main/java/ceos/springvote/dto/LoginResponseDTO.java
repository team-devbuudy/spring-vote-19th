package ceos.springvote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponseDTO {
    private String loginId;
    private String username;
    private String part;
    private String teamName;
    private String email;
    private int voteCount;
    private String role;
    private String token;
}
