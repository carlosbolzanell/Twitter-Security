package sc.senai.twetter2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sc.senai.twetter2.model.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
