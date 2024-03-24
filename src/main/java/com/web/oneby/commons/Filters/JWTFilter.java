package com.web.oneby.commons.Filters;

import com.web.oneby.modules.users.Models.User;
import com.web.oneby.modules.users.Services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    private UserService userService;

    @Autowired
    public JWTFilter(
            UserService userService
    ){
        this.userService = userService;
    }

    private final String SECRET_KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public User parseUser(String token){
        String username = Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return (User) userService.loadUserByUsername(username);
    }

    public boolean validation(String token){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Token claims string is empty: " + e.getMessage());
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getServletPath().contains("/api/v1/auth/confirm") ||
            !request.getServletPath().equals("/api/v1/auth/register") ||
            !(
                request.getServletPath().equals("/api/v1/auth/login") &&
                request.getMethod().equals(HttpMethod.POST.name())
            )
        ) {
            String token = "";
            if (request.getHeader("Authorization") != null &&
                    request.getHeader("Authorization").length() > 7 &&
                    request.getHeader("Authorization").startsWith("Bearer ")) {
                token = request.getHeader("Authorization").substring(7);
            }
            try {
                if (!token.isEmpty() && validation(token)) {
                    User user = parseUser(token);
                    SecurityContextHolder.getContext()
                            .setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getUsername(), user.getRoles()));
                }
            } catch (Exception e) {
                log.error("Error: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
