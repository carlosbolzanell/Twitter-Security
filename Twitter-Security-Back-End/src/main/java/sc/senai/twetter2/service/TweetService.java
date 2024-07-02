package sc.senai.twetter2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import sc.senai.twetter2.dto.CreateTweetDTO;
import sc.senai.twetter2.dto.FeedDTO;
import sc.senai.twetter2.dto.FeedItemDTO;
import sc.senai.twetter2.entity.Tweet;
import sc.senai.twetter2.entity.User;
import sc.senai.twetter2.repository.TweetRepository;
import sc.senai.twetter2.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public void createTweet(CreateTweetDTO tweetDTO, JwtAuthenticationToken token) throws Exception {
        User user = userRepository.findById(Long.parseLong(token.getName())).orElse(null);

        if(user == null){
            throw new Exception("No have user to reference this tweet!");
        }

        Tweet newTweet = new Tweet(null, user, tweetDTO.content(), null);
        tweetRepository.save(newTweet);
    }

    public FeedDTO feed(Integer page, Integer pageSize){
        Page<FeedItemDTO> tweets = tweetRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
                .map(tweet -> new FeedItemDTO(tweet.getTweetId(), tweet.getContet(), tweet.getUser().getUsername()));

        return new FeedDTO(tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements());

    }

}
