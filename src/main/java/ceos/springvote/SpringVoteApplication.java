package ceos.springvote;

import ceos.springvote.exception.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringVoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringVoteApplication.class, args);
    }

}
