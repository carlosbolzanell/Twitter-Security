package sc.senai.twetter2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import sc.senai.twetter2.dto.CreateTweetDTO;
import sc.senai.twetter2.dto.FeedDTO;
import sc.senai.twetter2.service.TweetService;

@RestController
@RequestMapping("/tweet")
@CrossOrigin("*")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @GetMapping("/feed")
    public ResponseEntity<FeedDTO> feed(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok(tweetService.feed(page, pageSize));
    }


    @PostMapping("/create")
    public ResponseEntity<String> createTweet(@RequestBody CreateTweetDTO tweetDTO,
                                              JwtAuthenticationToken token){
        try{
            tweetService.createTweet(tweetDTO, token);
            return ResponseEntity.ok("Just Tweeted!");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


}
