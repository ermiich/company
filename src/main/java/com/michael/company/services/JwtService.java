package com.michael.company.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

  private final String SECRET_KEY = "your-256-bit-secret-your-256-bit-secret"; // Usa una clave secreta segura

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, username);
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean validateToken(String token, String username) {
    final String extractedUsername = extractUsername(token);
    return (extractedUsername.equals(username) && !isTokenExpired(token));
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .setSigningKey(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public boolean isTokenExpired(String token) {
    try {
      return extractClaim(token, Claims::getExpiration).before(new Date());
    } catch (Exception e) {
      return true; 
    }
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

}