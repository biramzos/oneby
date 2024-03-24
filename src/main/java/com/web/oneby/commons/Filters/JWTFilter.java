package com.web.oneby.commons.Filters;

import com.web.oneby.commons.Enums.LogType;
import com.web.oneby.commons.Utils.LogUtil;
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
                LogUtil.write("Error: " + e.getMessage(), LogType.ERROR);
            }
        }
        filterChain.doFilter(request, response);
    }
}
