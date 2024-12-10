package alten.shop.AltenShop.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // La clé secrète doit être générée de manière sécurisée
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 heures

    // Génération du JWT
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)  // Utilisez l'email comme sujet
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();  // Si l'email est stocké dans le "subject"
    }

    // Vérifier si le token est expiré ou non
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extraire la date d'expiration du JWT
    private Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean validateToken(String token, String username) {
        return (username.equals(extractEmail(token)) && !isTokenExpired(token));
    }
}

