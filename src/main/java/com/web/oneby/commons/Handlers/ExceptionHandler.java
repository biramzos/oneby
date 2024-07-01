package com.web.oneby.commons.Handlers;

import com.web.oneby.commons.DTOs.MessageData;
import com.web.oneby.commons.DTOs.TemplateDataObject;
import com.web.oneby.commons.Enums.HTTPMessage;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Utils.LogUtil;
import com.web.oneby.commons.DTOs.Response;
import com.web.oneby.commons.Utils.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.util.HashMap;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public static ResponseEntity<Response> handleExceptions (HttpServletRequest request, HttpServletResponse response, Exception exception) {
        Language language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language"))   :
                Language.ru;
        Object message;
        LogUtil.write(exception);
        if (exception instanceof AccessDeniedException) {
            message = new MessageData();
            ((MessageData) message).set(HTTPMessage.ACCESS_DENIED, language);
        } else if (exception instanceof DisabledException) {
            message = new MessageData();
            ((MessageData) message).set(HTTPMessage.USER_IS_DISABLED, language);
        } else if (exception instanceof BadCredentialsException) {
            message = new MessageData();
            ((MessageData) message).set(HTTPMessage.USERNAME_OR_PASSWORD_IS_WRONG, language);
        } else if (exception instanceof MissingPathVariableException) {
            message = new MessageData();
            ((MessageData) message).set(HTTPMessage.ENTITY_IS_NOT_FOUND,language);
        } else if (exception instanceof NullPointerException) {
            message = new MessageData();
            if (exception.getMessage().contains(Authentication.class.getName())) {
                ((MessageData) message).set(HTTPMessage.FAILED_AUTHENTICATION, language);
            } else {
                ((MessageData) message).set(HTTPMessage.NULL_POINTER, language);
            }
        } else if (exception instanceof NoResourceFoundException) {
            message = new MessageData();
            ((MessageData) message).set(HTTPMessage.NO_RESOURCES_FOUND, language);
        } else if (exception instanceof HttpMessageNotReadableException) {
            message = new MessageData();
            ((MessageData) message).set(HTTPMessage.CANNOT_PARSE_DATA, language);
        } else if (exception instanceof MissingRequestHeaderException) {
            message = new MessageData();
            ((MessageData) message).set(HTTPMessage.MISSING_REQUEST_HEADER, language);
        } else if (exception instanceof UsernameNotFoundException) {
            message = new MessageData();
            ((MessageData) message).set(HTTPMessage.USER_NOT_FOUND, language);
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = new MessageData();
            ((MessageData) message).set(HTTPMessage.USER_IS_NOT_EXIST, language);
        } else if (exception instanceof MethodArgumentNotValidException) {
            message = new HashMap<String, MessageData>();
            ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors().forEach(fieldError -> {
                ((HashMap<String, MessageData>) message).put(fieldError.getField(), new MessageData(HTTPMessage.valueOf(fieldError.getDefaultMessage()), language));
            });
        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            message = new MessageData();
            ((MessageData) message).set(HTTPMessage.LANGUAGE_IS_NOT_EXIST, language);
        } else {
            message = new MessageData();
            ((MessageData) message).set(HTTPMessage.ERROR, language);
        }
        return ResponseEntity.badRequest().body(Response.getResponse("message", message));
    }

}
