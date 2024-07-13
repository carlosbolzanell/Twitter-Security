package sc.senai.twetter2.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import sc.senai.twetter2.dto.*;
import sc.senai.twetter2.entity.User;
import sc.senai.twetter2.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> addUser(@RequestBody CreateUserDTO user) {
        try{
            userService.createUser(user);
            return ResponseEntity.ok("User registered successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/getUserAuth")
    public ResponseEntity<UserResponseDTO> getUser(JwtAuthenticationToken jwt){
        try {
            return ResponseEntity.ok(userService.getUser(jwt));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getTweetsUser")
    public ResponseEntity<FeedDTO> getTweetsUser(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                 JwtAuthenticationToken jwt){
        return ResponseEntity.ok(userService.getTweetsUser(page, pageSize, jwt));
    }
    
}
