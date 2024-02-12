package com.web.oneby.commons.Controllers;

import com.web.oneby.commons.DTOs.CreateUserRequest;
import com.web.oneby.commons.DTOs.LoginUserRequest;
import com.web.oneby.commons.DTOs.UserResponse;
import com.web.oneby.commons.Enums.HTTPMessage;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Handlers.HTTPMessageHandler;
import com.web.oneby.commons.Models.User;
import com.web.oneby.commons.Services.UserService;
import com.web.oneby.commons.Utils.Response;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

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
            @ModelAttribute CreateUserRequest createUserRequest,
            @RequestHeader("Accept-Language") Language language
    ) throws IOException {
        Response response = new Response();
        HTTPMessageHandler messageHandler = new HTTPMessageHandler();
        User user = userService.create(createUserRequest, messageHandler, language.getId());
        if (user != null){
            response.put("user", UserResponse.fromUser(user, language.getId()));
        }
        response.put("message", messageHandler);
        return response;
    }

    @ResponseBody
    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    public Response loginUserPost(HttpServletResponse resp, @RequestBody LoginUserRequest loginUserRequest, @RequestHeader("Accept-Language") Language language){
        Response response = new Response();
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserRequest.getUsername(), loginUserRequest.getPassword()));
        resp.addCookie(new Cookie("role", String.join(",", ((User)auth.getPrincipal()).getRoles().stream().map(Enum::name).toList())));
        response.put("user", UserResponse.fromUser((User) auth.getPrincipal(), language.getId()));
        response.put("message", new HTTPMessageHandler(HTTPMessage.SUCCESSFULLY_LOGIN, language.getId()));
        return response;
    }

    @ResponseBody
    @GetMapping("/login")
    @PreAuthorize("isAuthenticated()")
    public Response loginUserGet(Authentication auth, @RequestHeader("Accept-Language") Language language){
        Response response = new Response();
        response.put("user", UserResponse.fromUser((User) auth.getPrincipal(), language.getId()));
        response.put("message", new HTTPMessageHandler(HTTPMessage.SUCCESSFULLY_LOGIN, language.getId()));
        return response;
    }

    @ResponseBody
    @GetMapping("/confirm/{token}")
    @PreAuthorize("isAnonymous()")
    public Response confirm(
            @RequestHeader("Accept-Language") Language language,
            @PathVariable("token") String token
    ){
        Response response = new Response();
        HTTPMessageHandler messageHandler = new HTTPMessageHandler();
        userService.confirm(token, messageHandler, language.getId());
        response.put("message", messageHandler);
        return response;
    }


    @ResponseBody
    @GetMapping(value = "/images/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public byte[] getImage(@PathVariable("userId") User user, @RequestHeader("Accept-Language") Language language){
        return user.getImage();
    }

}
