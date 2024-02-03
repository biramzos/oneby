package com.web.oneby.commons.Handlers;

import com.web.oneby.commons.Enums.HTTPMessage;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Utils.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public static Response handleAccessDeniedException(HttpServletRequest request, HttpServletResponse response,
                                    AccessDeniedException exception) throws IOException, ServletException {
        Response res = new Response();
        int language = Language.valueOf(request.getHeader("Accept-Language")).getId();
        log.error(exception.getMessage());
        res.put("message", new HTTPMessageHandler(HTTPMessage.ACCESS_DENIED, language));
        return res;
    }

    @ExceptionHandler(DisabledException.class)
    public static Response handleDisabledException(HttpServletRequest request, HttpServletResponse response,
                                       DisabledException exception) throws IOException, ServletException {
        Response res = new Response();
        int language = Language.valueOf(request.getHeader("Accept-Language")).getId();
        log.error(exception.getMessage());
        res.put("message", new HTTPMessageHandler(HTTPMessage.USER_IS_DISABLED, language));
        return res;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public static Response handleBadCredentialsException(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) throws IOException, ServletException {
        Response res = new Response();
        int language = Language.valueOf(request.getHeader("Accept-Language")).getId();
        log.error(exception.getMessage());
        res.put("message", new HTTPMessageHandler(HTTPMessage.USERNAME_OR_PASSWORD_IS_WRONG, language));
        return res;
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public static Response handleMissingPathVariableException (HttpServletRequest request, HttpServletResponse response,
                                       MissingPathVariableException exception) throws IOException, ServletException {
        Response res = new Response();
        int language = Language.valueOf(request.getHeader("Accept-Language")).getId();
        log.error(exception.getMessage());
        res.put("message", new HTTPMessageHandler(HTTPMessage.ENTITY_IS_NOT_FOUND,language));
        return res;
    }

    @ExceptionHandler(NullPointerException.class)
    public static Response handleNullPointerException (HttpServletRequest request, HttpServletResponse response,
                                       NullPointerException exception) throws IOException, ServletException {
        Response res = new Response();
        int language = Language.valueOf(request.getHeader("Accept-Language")).getId();
        log.error(exception.getMessage());
        if (exception.getMessage().contains(Authentication.class.getName())) {
            res.put("message", new HTTPMessageHandler(HTTPMessage.FAILED_AUTHENTICATION, language));
        } else {
            res.put("message", new HTTPMessageHandler(HTTPMessage.NULL_POINTER, language));
        }
        return res;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public static Response handleNoResourceFoundException (HttpServletRequest request, HttpServletResponse response,
                                       NoResourceFoundException exception) throws IOException, ServletException {
        Response res = new Response();
        int language = Language.valueOf(request.getHeader("Accept-Language")).getId();
        log.error(exception.getMessage());
        res.put("message", new HTTPMessageHandler(HTTPMessage.NO_RESOURCES_FOUND, language));
        return res;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public static Response handleHttpMessageNotReadableException (HttpServletRequest request, HttpServletResponse response,
                                      HttpMessageNotReadableException exception) throws IOException, ServletException {
        Response res = new Response();
        int language = Language.valueOf(request.getHeader("Accept-Language")).getId();
        log.error(exception.getMessage());
        res.put("message", new HTTPMessageHandler(HTTPMessage.CANNOT_PARSE_DATA, language));
        return res;
    }

    @ExceptionHandler(Exception.class)
    public static Response handleException (HttpServletRequest request, HttpServletResponse response,
                                       Exception exception) throws IOException, ServletException {
        Response res = new Response();
        int language = Language.valueOf(request.getHeader("Accept-Language")).getId();
        log.error(exception.getMessage());
        res.put("message", new HTTPMessageHandler(HTTPMessage.ERROR, language));
        return res;
    }

}
