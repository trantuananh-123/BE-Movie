package phuongnguyen.movie.Util;

import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import phuongnguyen.movie.Table.Model.UserDetail;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtil {

    private final Logger logger = LogManager.getLogger(JwtUtil.class);

    @Value(value = "${movie.tokenSecret}")
    private String tokenSecret;

    @Value(value = "${movie.tokenExpirationTime}")
    private long tokenExpirationTime;

    public String generateToken(Authentication authentication) {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetail.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + tokenExpirationTime))
                .signWith(SignatureAlgorithm.HS256, tokenSecret)
                .compact();
    }

    public String generateTokenFromUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + tokenExpirationTime))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public LocalDateTime extractExpirationTime(String token) {
        Date date = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody().getExpiration();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
