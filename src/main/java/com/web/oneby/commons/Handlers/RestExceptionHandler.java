package com.web.oneby.commons.Handlers;

import com.web.oneby.commons.Enums.HTTPMessage;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Enums.LogType;
import com.web.oneby.commons.Utils.LogUtil;
import com.web.oneby.commons.Utils.Response;
import com.web.oneby.commons.Utils.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public static Response handleAccessDeniedException(HttpServletRequest request, HttpServletResponse response,
                                    AccessDeniedException exception) {
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language")).getId()   :
                Language.ru.getId();
        message.set(HTTPMessage.ACCESS_DENIED, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        return Response.getResponse("message", message);
    }

    @ExceptionHandler(DisabledException.class)
    public static Response handleDisabledException(HttpServletRequest request, HttpServletResponse response,
                                       DisabledException exception) {
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language")).getId()   :
                Language.ru.getId();
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        message.set(HTTPMessage.USER_IS_DISABLED, language);
        return Response.getResponse("message", message);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public static Response handleBadCredentialsException(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) {
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language")).getId()   :
                Language.ru.getId();
        message.set(HTTPMessage.USERNAME_OR_PASSWORD_IS_WRONG, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        return Response.getResponse("message", message);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public static Response handleMissingPathVariableException (HttpServletRequest request, HttpServletResponse response,
                                       MissingPathVariableException exception) {
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language")).getId()   :
                Language.ru.getId();
        message.set(HTTPMessage.ENTITY_IS_NOT_FOUND,language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        return Response.getResponse("message", message);
    }

    @ExceptionHandler(NullPointerException.class)
    public static Response handleNullPointerException (HttpServletRequest request, HttpServletResponse response,
                                       NullPointerException exception) {
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language")).getId()   :
                Language.ru.getId();
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        if (exception.getMessage().contains(Authentication.class.getName())) {
            message.set(HTTPMessage.FAILED_AUTHENTICATION, language);
        } else {
            message.set(HTTPMessage.NULL_POINTER, language);
        }
        return Response.getResponse("message", message);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public static Response handleNoResourceFoundException (HttpServletRequest request, HttpServletResponse response,
                                       NoResourceFoundException exception) {
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language")).getId()   :
                Language.ru.getId();
        message.set(HTTPMessage.NO_RESOURCES_FOUND, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        return Response.getResponse("message", message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public static Response handleHttpMessageNotReadableException (HttpServletRequest request, HttpServletResponse response,
                                      HttpMessageNotReadableException exception) {
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language")).getId()   :
                Language.ru.getId();
        message.set(HTTPMessage.CANNOT_PARSE_DATA, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        return Response.getResponse("message", message);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public static Response handleMissingRequestHeaderException (HttpServletRequest request, HttpServletResponse response,
                                        MissingRequestHeaderException exception) {
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language")).getId()   :
                Language.ru.getId();
        message.set(HTTPMessage.MISSING_REQUEST_HEADER, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        return Response.getResponse("message", message);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public static Response handleUsernameNotFoundException (HttpServletRequest request, HttpServletResponse response,
                                        UsernameNotFoundException exception) {
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language")).getId()   :
                Language.ru.getId();
        message.set(HTTPMessage.USER_NOT_FOUND, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        return Response.getResponse("message", message);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public static Response handleInternalAuthenticationServiceException (HttpServletRequest request, HttpServletResponse response,
                                         InternalAuthenticationServiceException exception) {
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language")).getId()   :
                Language.ru.getId();
        message.set(HTTPMessage.USER_IS_NOT_EXIST, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        return Response.getResponse("message", message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public static Response handleMethodArgumentNotValidException (HttpServletRequest request, HttpServletResponse response,
                                      MethodArgumentNotValidException exception) {
        Map<String, HTTPMessageHandler> messages = new HashMap<>();
        int language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language")).getId()   :
                Language.ru.getId();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            messages.put(fieldError.getField(), new HTTPMessageHandler(HTTPMessage.valueOf(fieldError.getDefaultMessage()), language));
        });
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        return Response.getResponse("message", messages);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public static Response handleMethodArgumentTypeMismatchException (HttpServletRequest request, HttpServletResponse response,
                                      MethodArgumentTypeMismatchException exception) {
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language")).getId()   :
                Language.ru.getId();
        message.set(HTTPMessage.LANGUAGE_IS_NOT_EXIST, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        return Response.getResponse("message", message);
    }

    @ExceptionHandler(Exception.class)
    public static Response handleException (HttpServletRequest request, HttpServletResponse response,
                                       Exception exception) {
        HTTPMessageHandler message = new HTTPMessageHandler();
        int language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language")).getId()   :
                Language.ru.getId();
        message.set(HTTPMessage.ERROR, language);
        LogUtil.write(exception.getMessage(), LogType.ERROR);
        return Response.getResponse("message", message);
    }

}
