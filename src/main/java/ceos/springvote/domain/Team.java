package ceos.springvote.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Slf4j
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer voteCount = 0;


    public void addVoteCount() {
        this.voteCount ++;
    }

    public Optional<Integer> subVoteCount() {
        int temp = this.voteCount;
        if (temp == 0) {
            return Optional.empty();
        }
        this.voteCount--;
        return Optional.of(temp);
    }
}
