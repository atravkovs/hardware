package org.xapik.hardware.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {

  @Serial
  private static final long serialVersionUID = -2550185165626007488L;

  public static final long JWT_TOKEN_VALIDITY = 5 * 60 * (long) 60;

  @Value("${jwt.secret}")
  private String secret;

  public UserDetails getUserDetailsFromToken(String token) {
    return new User(this.getUsernameFromToken(token), "", new ArrayList<>());
  }

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getIssuedAtDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getIssuedAt);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, @NotNull Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsForToken(token);

    return claimsResolver.apply(claims);
  }

  public String generateToken(@NotNull UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("test", "value");
    return doGenerateToken(claims, userDetails.getUsername());
  }

  public Boolean validateToken(String token) {
    return !isTokenExpired(token);
  }

  public Boolean canTokenBeRefreshed(String token) {
    return (!isTokenExpired(token) || ignoreTokenExpiration(token));
  }

  private Claims getAllClaimsForToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  private @NotNull Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  private @NotNull Boolean ignoreTokenExpiration(String token) {
    final String[] ignoreTokens = {""};

    return Arrays.asList(ignoreTokens).contains(token);
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder().setClaims(claims).setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }


}
