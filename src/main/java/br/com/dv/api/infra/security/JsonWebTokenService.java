package br.com.dv.api.infra.security;

import br.com.dv.api.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JsonWebTokenService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("REST API Issuer")
                    .withSubject(user.getUsername())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error when trying to generate a token", exception);
        }
    }

    private Date getExpirationDate() {
        var zoneId = ZoneId.of("America/Sao_Paulo");
        var zonedDateTime = LocalDateTime.now().plusHours(2).atZone(zoneId);
        var instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    public String validateTokenAndGetSubject(String token) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("REST API Issuer")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid JWT!");
        }
    }

}
