package sc.senai.twetter2.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
