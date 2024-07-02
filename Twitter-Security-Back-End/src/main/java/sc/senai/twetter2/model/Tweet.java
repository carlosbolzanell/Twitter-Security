package sc.senai.twetter2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_tweets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tweetId;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    private String contet;

    @CreationTimestamp
    private Instant creationTimestamp;
}
