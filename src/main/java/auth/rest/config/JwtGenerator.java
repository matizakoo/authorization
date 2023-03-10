package auth.rest.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {
    public String generateToken(Authentication authentication){
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JwtConstants.JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, JwtConstants.JWT_SECRET)
                .compact();
    }

    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JwtConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(JwtConstants.JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("JWT problem expired or incorrect :(");
        }
    }

    public int getExpirationInMillis(){
        return JwtConstants.JWT_EXPIRATION;
    }
}
