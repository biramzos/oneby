package com.web.oneby.Handlers;

import com.web.oneby.Enums.HTTPMessage;
import com.web.oneby.Enums.Language;
import com.web.oneby.Utils.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.IOException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public static Response handleAccessDeniedException(HttpServletRequest request, HttpServletResponse response,
                                                AccessDeniedException e) throws IOException, ServletException {
        Response res = new Response();
        String[] urlParts = request.getRequestURI().split("/");
        String language = urlParts[urlParts.length - 1];
        res.put("message", new HTTPMessageHandler(HTTPMessage.ACCESS_DENIED, Language.valueOf(language).getId()));
        return res;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public static Response handleBadCredentialsException(HttpServletRequest request, HttpServletResponse response,
                                                  AuthenticationException exception) throws IOException, ServletException {
        Response res = new Response();
        String[] urlParts = request.getRequestURI().split("/");
        String language = urlParts[urlParts.length - 1];
        res.put("message", new HTTPMessageHandler(HTTPMessage.USERNAME_OR_PASSWORD_IS_WRONG, Language.valueOf(language).getId()));
        return res;
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public static Response handleMissingPathVariableException (HttpServletRequest request, HttpServletResponse response,
                                                   MissingPathVariableException exception) throws IOException, ServletException {
        Response res = new Response();
        String[] urlParts = request.getRequestURI().split("/");
        String language = urlParts[urlParts.length - 1];
        res.put("message", new HTTPMessageHandler(HTTPMessage.ENTITY_IS_NOT_FOUND, Language.valueOf(language).getId()));
        return res;
    }

}
