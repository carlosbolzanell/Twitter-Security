package sc.senai.twetter2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import sc.senai.twetter2.dto.CreateUserDTO;
import sc.senai.twetter2.dto.FeedDTO;
import sc.senai.twetter2.dto.FeedItemDTO;
import sc.senai.twetter2.dto.UserResponseDTO;
import sc.senai.twetter2.entity.Role;
import sc.senai.twetter2.entity.Tweet;
import sc.senai.twetter2.entity.User;
import sc.senai.twetter2.repository.RoleRepository;
import sc.senai.twetter2.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TweetService tweetService;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createUser(CreateUserDTO user) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername(user.username());
        Role basicRole = roleRepository.findByName(Role.Values.BASIC.name());

        if (optionalUser.isPresent()) {
            throw new Exception("User already exist on database!");
        }
        User userCreated = new User(null, user.username(), user.email(), passwordEncoder.encode(user.password()), Set.of(basicRole));

        userRepository.save(userCreated);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public UserResponseDTO getUser(JwtAuthenticationToken jwt) throws Exception {
        Optional<User> userOptional = userRepository.findById(Long.parseLong(jwt.getName()));
        if(userOptional.isEmpty()){
            throw new Exception("User not found!");
        }
        User user = userOptional.get();
        return new UserResponseDTO(user.getUsername(), user.getEmail());
    }

    public FeedDTO getTweetsUser(Integer page, Integer pageSize, JwtAuthenticationToken jwt) {

        Long idUser = Long.parseLong(jwt.getName());
        List<Tweet> tweetUser = new ArrayList<>();

        for(Tweet tweet : tweetService.getAllTweets()){
            if(tweet.getUser().getUserId().equals(idUser)){
                tweetUser.add(tweet);
            }
        }


        return new FeedDTO();

    }
}
