package com.web.oneby.Filters;

import com.web.oneby.Models.User;
import com.web.oneby.Services.UserService;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final String SECRETKEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String parseJwt(String token){
        return Jwts
                .parser()
                .setSigningKey(SECRETKEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validation(String token){
        try{
            Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            System.err.println("Invalid token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("Token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Token claims string is empty: " + e.getMessage());
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = "";
        if(request.getHeader("Authorization") != null &&
                request.getHeader("Authorization").length() > 7 &&
                request.getHeader("Authorization").startsWith("Bearer ")){
            token = request.getHeader("Authorization").substring(7);
        } else {
            token = null;
        }
        try{
            if(token != null && validation(token)){
                String username = parseJwt(token);
                User user = (User) userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, user.getUsername(), user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception e){
            System.err.println("Error " + e.getMessage());
        }
        filterChain.doFilter(request,response);
    }
}
