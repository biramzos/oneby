package com.web.oneby.commons.Handlers;

import com.web.oneby.commons.Enums.HTTPMessage;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Enums.LogType;
import com.web.oneby.commons.Utils.LogUtil;
import com.web.oneby.commons.Utils.Response;
import com.web.oneby.commons.Utils.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public static Response handleAccessDeniedException(HttpServletRequest request, HttpServletResponse response,
                                    AccessDeniedException exception) {
        Response res = new Response();
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isEmpty(request.getHeader("Current-Language"))) ?
                Language.ru.getId()    :
                Language.valueOf(request.getHeader("Current-Language")).getId();
        message.set(HTTPMessage.ACCESS_DENIED, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        res.put("message", message);
        return res;
    }

    @ExceptionHandler(DisabledException.class)
    public static Response handleDisabledException(HttpServletRequest request, HttpServletResponse response,
                                       DisabledException exception) {
        Response res = new Response();
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isEmpty(request.getHeader("Current-Language"))) ?
                Language.ru.getId()    :
                Language.valueOf(request.getHeader("Current-Language")).getId();
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        message.set(HTTPMessage.USER_IS_DISABLED, language);
        res.put("message", message);
        return res;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public static Response handleBadCredentialsException(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) {
        Response res = new Response();
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isEmpty(request.getHeader("Current-Language"))) ?
                Language.ru.getId()    :
                Language.valueOf(request.getHeader("Current-Language")).getId();
        message.set(HTTPMessage.USERNAME_OR_PASSWORD_IS_WRONG, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        res.put("message", message);
        return res;
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public static Response handleMissingPathVariableException (HttpServletRequest request, HttpServletResponse response,
                                       MissingPathVariableException exception) {
        Response res = new Response();
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isEmpty(request.getHeader("Current-Language"))) ?
                Language.ru.getId()    :
                Language.valueOf(request.getHeader("Current-Language")).getId();
        message.set(HTTPMessage.ENTITY_IS_NOT_FOUND,language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        res.put("message", message);
        return res;
    }

    @ExceptionHandler(NullPointerException.class)
    public static Response handleNullPointerException (HttpServletRequest request, HttpServletResponse response,
                                       NullPointerException exception) {
        Response res = new Response();
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isEmpty(request.getHeader("Current-Language"))) ?
                Language.ru.getId()    :
                Language.valueOf(request.getHeader("Current-Language")).getId();
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        if (exception.getMessage().contains(Authentication.class.getName())) {
            message.set(HTTPMessage.FAILED_AUTHENTICATION, language);
        } else {
            message.set(HTTPMessage.NULL_POINTER, language);
        }
        res.put("message", message);
        return res;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public static Response handleNoResourceFoundException (HttpServletRequest request, HttpServletResponse response,
                                       NoResourceFoundException exception) {
        Response res = new Response();
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isEmpty(request.getHeader("Current-Language"))) ?
                Language.ru.getId()    :
                Language.valueOf(request.getHeader("Current-Language")).getId();
        message.set(HTTPMessage.NO_RESOURCES_FOUND, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        res.put("message", message);
        return res;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public static Response handleHttpMessageNotReadableException (HttpServletRequest request, HttpServletResponse response,
                                      HttpMessageNotReadableException exception) {
        Response res = new Response();
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isEmpty(request.getHeader("Current-Language"))) ?
                Language.ru.getId()    :
                Language.valueOf(request.getHeader("Current-Language")).getId();
        message.set(HTTPMessage.CANNOT_PARSE_DATA, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        res.put("message", message);
        return res;
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public static Response handleMissingRequestHeaderException (HttpServletRequest request, HttpServletResponse response,
                                        MissingRequestHeaderException exception) {
        Response res = new Response();
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isEmpty(request.getHeader("Current-Language"))) ?
                Language.ru.getId()    :
                Language.valueOf(request.getHeader("Current-Language")).getId();
        message.set(HTTPMessage.MISSING_REQUEST_HEADER, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        res.put("message", message);
        return res;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public static Response handleUsernameNotFoundException (HttpServletRequest request, HttpServletResponse response,
                                        UsernameNotFoundException exception) {
        Response res = new Response();
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isEmpty(request.getHeader("Current-Language"))) ?
                Language.ru.getId()    :
                Language.valueOf(request.getHeader("Current-Language")).getId();
        message.set(HTTPMessage.USER_NOT_FOUND, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        res.put("message", message);
        return res;
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public static Response handleInternalAuthenticationServiceException (HttpServletRequest request, HttpServletResponse response,
                                         InternalAuthenticationServiceException exception) {
        Response res = new Response();
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isEmpty(request.getHeader("Current-Language"))) ?
                Language.ru.getId()    :
                Language.valueOf(request.getHeader("Current-Language")).getId();
        message.set(HTTPMessage.USER_IS_NOT_EXIST, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        res.put("message", message);
        return res;
    }

    @ExceptionHandler(Exception.class)
    public static Response handleException (HttpServletRequest request, HttpServletResponse response,
                                       Exception exception) {
        Response res = new Response();
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isEmpty(request.getHeader("Current-Language"))) ?
                Language.ru.getId()    :
                Language.valueOf(request.getHeader("Current-Language")).getId();
        message.set(HTTPMessage.ERROR, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        res.put("message", message);
        return res;
    }

}
