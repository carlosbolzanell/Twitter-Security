package sc.senai.twetter2.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import sc.senai.twetter2.dto.LoginRequest;
import sc.senai.twetter2.dto.LoginResponse;
import sc.senai.twetter2.dto.UserResponseDTO;
import sc.senai.twetter2.service.TokenService;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class TokenController {

    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(tokenService.login(loginRequest));
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserResponseDTO> getUserByToken(JwtAuthenticationToken token){
        return ResponseEntity.ok(tokenService.getUser(token));
    }

}
