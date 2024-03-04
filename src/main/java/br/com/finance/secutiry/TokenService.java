package br.com.finance.secutiry;

import java.time.Instant;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import br.com.finance.models.User;

@Service
public class TokenService {

  @Value("${api.secutiry.token.secret}")
  private String secret;

  public String generateToken(User user) {

    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create()
          .withIssuer("finance-api")
          .withSubject(user.getEmail())
          .withExpiresAt(Instant.now().plusSeconds(60 * 60 * 4))
          .sign(algorithm);
      return token;
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Erro ao gerar token", exception);
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);

      var verifier = JWT.require(algorithm)
          .withIssuer("finance-api")
          .build();

      var decodedToken = verifier.verify(token);

      var subject = decodedToken.getSubject();

      return subject;

    } catch (Exception e) {
      return "";
    }
  }


}
