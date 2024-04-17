package com.web.oneby.commons.Filters;

import com.web.oneby.commons.Enums.LogType;
import com.web.oneby.commons.Utils.LogUtil;
import com.web.oneby.commons.Utils.TokenUtil;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public User parseUser(String token){
        return (User) userService.loadUserByUsername(TokenUtil.getUsernameFromToken(token));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            if (request.getServletPath().contains("/api/v1/auth/confirm") ||
                request.getServletPath().equals("/api/v1/auth/register") ||
                (request.getServletPath().equals("/api/v1/auth/login") && request.getMethod().equals(HttpMethod.POST.name()))
            ) {
                filterChain.doFilter(request, response);
                return;
            }
            String endpoint = request.getServletPath();
            String token = "";
            if (request.getHeader("Token") != null && !request.getHeader("Token").isEmpty()) {
                token = request.getHeader("Token");
            }
            if (!token.isEmpty() && TokenUtil.validateToken(token)) {
                String username = TokenUtil.getUsernameFromToken(token);
                User user = parseUser(token);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getUsername(), user.getRoles()));
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            LogUtil.write("Error: " + e.getMessage(), LogType.ERROR);
        }
    }
}
