package com.web.oneby.commons.Utils;

import com.web.oneby.commons.Enums.LogType;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    private static final String SECRET_KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final long EXPIRATION = 1000 * 60 * 60;

    public static String generateAccessToken(Claims claims){
        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String getAccessToken(String refreshToken){
        return refreshAccessToken(refreshToken);
    }

    public static String generateRefreshToken(Map<String, String> claims){
        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 24))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String getRefreshToken(String username) {
        Map<String,String> claims = new HashMap<>();
        claims.put("username", username);
        return generateRefreshToken(claims);
    }

    public static String refreshAccessToken(String refreshToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(refreshToken)
                    .getBody();
            return generateAccessToken(claims);
        } catch (JwtException e) {
            LogUtil.write(e);
            return null;
        }
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

    private static Claims getClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getUsernameFromToken(String token) {
        return (String) getClaims(token).get("username");
    }

}
