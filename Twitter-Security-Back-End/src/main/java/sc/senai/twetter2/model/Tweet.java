package sc.senai.twetter2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "tb_tweets")
@Getter
@Setter
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tweetId;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    private String contet;

    private Instant creationTimestamp;
}
