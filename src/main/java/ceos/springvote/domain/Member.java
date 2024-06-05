package ceos.springvote.domain;

import ceos.springvote.constant.Part;
import ceos.springvote.constant.Position;
import ceos.springvote.constant.Role;
import ceos.springvote.constant.Team;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String login_id;
    private String password;
    private String email;
    private Part part;
    private Position position;
    private Role role;
    private Team team;
}
