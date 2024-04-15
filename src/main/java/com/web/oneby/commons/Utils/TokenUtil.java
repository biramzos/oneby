package com.web.oneby.commons.Utils;

import com.web.oneby.commons.Enums.LogType;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class TokenUtil {

    private static final String SECRET_KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final long EXPIRATION = 1000 * 60 * 24;

    public static String generateAccessTokenByUsername(String username){
        return Jwts
                .builder()
                .claim("username", username)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String generateRefreshTokenByUsername(String username){
        return Jwts
                .builder()
                .claim("username", username)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            LogUtil.write("Invalid token: " + e.getMessage(), LogType.ERROR);
        } catch (ExpiredJwtException e) {
            LogUtil.write("Token is expired: " + e.getMessage(), LogType.ERROR);
        } catch (UnsupportedJwtException e) {
            LogUtil.write("Token is unsupported: " + e.getMessage(), LogType.ERROR);
        } catch (IllegalArgumentException e) {
            LogUtil.write("Token claims string is empty: " + e.getMessage(), LogType.ERROR);
        }
        return false;
    }

    public static String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
