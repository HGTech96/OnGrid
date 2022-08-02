package com.hg.dashboard.security;


import com.hg.dashboard.domain.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    @Value("${jwt.auth.app}")
    private String appname;

    @Value("${jwt.auth.secret_key}")
    private String secretKey;

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuer(this.appname)
//                .setIssuedAt(issuedAt)
//                .setExpiration(expiresOn)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Token expired: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespaces: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("Token is not valid: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Token is not supported: {}", ex.getMessage());
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed: {}", ex.getMessage());
        }
        return false;
    }

    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }
    public Long getUserId(String token) {
        return Long.parseLong(parseClaims(token).getSubject());
    }


    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
