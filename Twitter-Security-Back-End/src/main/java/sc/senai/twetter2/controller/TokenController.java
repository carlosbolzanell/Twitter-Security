package sc.senai.twetter2.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sc.senai.twetter2.dto.LoginRequest;
import sc.senai.twetter2.dto.LoginResponse;
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

}
