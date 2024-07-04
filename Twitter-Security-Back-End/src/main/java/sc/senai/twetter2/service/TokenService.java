package sc.senai.twetter2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import sc.senai.twetter2.dto.LoginRequest;
import sc.senai.twetter2.dto.LoginResponse;
import sc.senai.twetter2.dto.UserResponseDTO;
import sc.senai.twetter2.entity.Role;
import sc.senai.twetter2.entity.User;
import sc.senai.twetter2.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public LoginResponse login(LoginRequest loginRequest){
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.username());
        User user = (optionalUser.orElse(null));

        if(optionalUser.isEmpty() || passwordEncoder.matches(user.getPassword(), loginRequest.password())) {
            throw new BadCredentialsException("Credentials not found!");
        }

        Instant now = Instant.now();
        long expiresIn = 60L * 30L; //30 minutos

        String scopes = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(" ")).toUpperCase();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("tweeter2")
                .subject(user.getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("role", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue, expiresIn );
    }

    public UserResponseDTO getUser(JwtAuthenticationToken token) {
        System.out.println(token);
        return new UserResponseDTO("Carlos", "Carlos@email");
        //        Optional<User> optionalUser = userRepository.findById(Long.parseLong(token.getName()));
//        User user = optionalUser.orElse(null);
//        assert user != null;
//        return new UserResponseDTO(user.getUsername(), user.getEmail());
    }
}
