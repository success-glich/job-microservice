package com.jobms.auth_server.auth.utils;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;



@Component
@Slf4j
public class JwtUtils {

    @Value("${spring.jwt.secret:#{null}}")
    private String jwtSecret;

    @Value("${spring.jwt.expiration:#{null}}")
    private int expirationTime;

    @Value("${spring.jwt.issuer:#{null}}")
    private String issuer;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        log.debug("Authorization Header: {}", bearerToken);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public  String getJwtFromCookie(HttpServletRequest request){
        String jwt = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }
        return jwt;
    }
    public String getUsernameFromJwtToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key())
                    .requireIssuer(issuer)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException ex) {
            log.error("JWT token expired: {}", ex.getMessage());
        } catch (JwtException | IllegalArgumentException ex) {
            log.error("Failed to parse JWT token: {}", ex.getMessage());
        }
        return null;
    }
    public  String generateTokenFromUsername(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+expirationTime);
        return  Jwts.builder()
                .setIssuer(issuer)
                .setSubject(username)
                .signWith(key())
                .setExpiration(expiryDate)
                .compact();
    }
    public boolean validateJwtToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .requireIssuer(issuer)
                    .build()
                    .parseClaimsJws(token);

            return true;
        }catch (MalformedJwtException ex){
            log.error("Invalid JWT token: {}", ex.getMessage());
        }
        catch (ExpiredJwtException ex){
            log.error("JWT token validation failed: {}", ex.getMessage());
        }
        catch (UnsupportedJwtException ex){
            log.error("JWT token is unsupported: {}", ex.getMessage());
        }
        catch (IllegalArgumentException ex){
            log.error("JWT claims string is empty: {}", ex.getMessage());
        }
        return  false;
    }




}
