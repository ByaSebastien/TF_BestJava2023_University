package be.bstorm.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private JwtBuilder builder;
    private JwtParser parser;

    public JwtUtil() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        parser = Jwts.parserBuilder().setSigningKey(key).build();
        builder = Jwts.builder().signWith(key);
    }

    public String getUsernameFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getClaimsFromToken(String token) {
        return parser
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("creator", "Technofutur");

        return generateToken(claims, userDetails.getUsername());
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        return builder
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .compact();
    }
}
