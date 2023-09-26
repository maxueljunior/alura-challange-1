package br.com.leuxam.alura.challange1.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.leuxam.alura.challange1.domain.user.Users;

@Service
public class TokenService {
	
	@Value("${api.token.security.key}")
	private String secret;
	
	public String gerarToken(Users user) {
		
		try {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		
		return JWT.create()
				.withIssuer("API alura challenge 1")
				.withSubject(user.getUsername())
				.withExpiresAt(dataExpiracaoToken())
				.sign(algorithm);
		}catch(JWTCreationException exception) {
			throw new RuntimeException("Erro ao gerar token JWT " + exception);
		}
		
	}

	private Instant dataExpiracaoToken() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}

	public String getSubject(String tokenJWT) {
		
		DecodedJWT decodedJWT;
		
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer("API alura challenge 1")
					.build();
			
			return verifier.verify(tokenJWT).getSubject();
		}catch(JWTVerificationException exception) {
			throw new RuntimeException("Token JWT invalido ou expirado");
		}
	}
}
