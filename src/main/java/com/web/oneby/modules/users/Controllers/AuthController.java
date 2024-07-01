package com.web.oneby.modules.users.Controllers;

import com.web.oneby.commons.DTOs.MessageData;
import com.web.oneby.commons.DTOs.TemplateDataObject;
import com.web.oneby.commons.DTOs.TokenData;
import com.web.oneby.commons.Enums.HTTPMessage;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.DTOs.Response;
import com.web.oneby.commons.Utils.TokenUtil;
import com.web.oneby.modules.users.DTOs.CreateUserRequest;
import com.web.oneby.modules.users.DTOs.LoginUserRequest;
import com.web.oneby.modules.users.DTOs.UserResponse;
import com.web.oneby.modules.users.Models.User;
import com.web.oneby.modules.users.Services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager
    ){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @ResponseBody
    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("isAnonymous()")
    public Response registerUser(
            HttpServletResponse httpResponse,
            @ModelAttribute @Valid CreateUserRequest createUserRequest,
            @RequestHeader(value = "Current-Language", defaultValue = "ru") Language language
    ) {
        Response response = new Response();
        MessageData messageHandler = new MessageData();
        boolean isSaved = userService.create(createUserRequest, messageHandler, language);
        if (isSaved){
            TokenData token = new TokenData();
            token.setRefreshToken(TokenUtil.getRefreshToken(createUserRequest.getUsername()));
            token.setAccessToken(TokenUtil.getAccessToken(token.getRefreshToken()));
            response.put("token", token);
        }
        response.put("message", messageHandler);
        return response;
    }

    @ResponseBody
    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    public Response loginUserPost(@RequestBody LoginUserRequest loginUserRequest, @RequestHeader(value = "Current-Language", defaultValue = "ru") Language language){
        Response response = new Response();
        User user = ((User) authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(loginUserRequest.getUsername(), loginUserRequest.getPassword()))
                .getPrincipal());
        TokenData token = new TokenData();
        token.setRefreshToken(TokenUtil.getRefreshToken(loginUserRequest.getUsername()));
        token.setAccessToken(TokenUtil.getAccessToken(token.getRefreshToken()));
        response.put("token", token);
        response.put("message", new MessageData(HTTPMessage.SUCCESSFULLY_LOGIN, language));
        return response;
    }

    @ResponseBody
    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    public Response loginUserGet(Authentication auth,
             @RequestHeader(value = "Current-Language", defaultValue = "ru") Language language){
        Response response = new Response();
        User user = (User) auth.getPrincipal();
        response.put("user", UserResponse.fromUser(user, language));
        return response;
    }

    @ResponseBody
    @PostMapping("/refresh")
    @PreAuthorize("isAuthenticated()")
    public Response refreshToken(Authentication auth) {
        Response response = new Response();
        TokenData token = new TokenData();
        token.setRefreshToken(TokenUtil.getRefreshToken(((User) auth.getPrincipal()).getUsername()));
        token.setAccessToken(TokenUtil.getAccessToken(token.getRefreshToken()));
        response.put("token", token);
        return response;
    }

    @ResponseBody
    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public Response logout(HttpServletResponse httpServletResponse,
                           @RequestHeader(value = "Current-Language", defaultValue = "ru") Language language){
        Response response = new Response();
        SecurityContextHolder.getContext().setAuthentication(null);
        httpServletResponse.setHeader("Token", null);
        response.put("message", new MessageData(HTTPMessage.SUCCESSFULLY_LOGOUT, language));
        return response;
    }

    @ResponseBody
    @GetMapping("/confirm/{token}")
    @PreAuthorize("isAnonymous()")
    public Response confirm(
            @RequestHeader(value = "Current-Language", defaultValue = "ru") Language language,
            @PathVariable("token") String token
    ){
        Response response = new Response();
        MessageData messageHandler = new MessageData();
        userService.confirm(token, messageHandler, language);
        response.put("message", messageHandler);
        return response;
    }


    @ResponseBody
    @GetMapping(value = "/images/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public byte[] getImage(@PathVariable("userId") User user, @RequestHeader(value = "Current-Language", defaultValue = "ru") Language language) {
        return user.getImage();
    }

}
