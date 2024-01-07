package com.web.oneby.Controllers;

import com.web.oneby.DTO.CreateUserRequest;
import com.web.oneby.DTO.LoginUserRequest;
import com.web.oneby.DTO.UserResponse;
import com.web.oneby.Enums.HTTPMessage;
import com.web.oneby.Enums.Language;
import com.web.oneby.Handlers.HTTPMessageHandler;
import com.web.oneby.Models.User;
import com.web.oneby.Services.EmailService;
import com.web.oneby.Services.UserService;
import com.web.oneby.Utils.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.AccessDeniedException;

import static jakarta.servlet.http.HttpServletResponse.*;

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
    @PostMapping(value = "/register/{language}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("isAnonymous()")
    public Response registerUser(
            @ModelAttribute CreateUserRequest createUserRequest,
            @PathVariable("language") Language language
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
    @PostMapping("/login/{language}")
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public Response loginUser(@RequestBody LoginUserRequest loginUserRequest, @PathVariable("language") Language language){
        Response response = new Response();
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserRequest.getUsername(), loginUserRequest.getPassword()));
        response.put("user", UserResponse.fromUser((User) auth.getPrincipal(), language.getId()));
        response.put("message", new HTTPMessageHandler(HTTPMessage.SUCCESSFULLY_LOGIN, language.getId()));
        return response;
    }

    @ResponseBody
    @GetMapping("/info/{language}")
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public Response getInfo(Authentication auth, @PathVariable("language") Language language){
        Response response = new Response();
        response.put("user", UserResponse.fromUser((User) auth.getPrincipal(), language.getId()));
        return response;
    }

    @ResponseBody
    @GetMapping("/confirm/{token}/{language}")
    @PreAuthorize("isAnonymous()")
    public Response confirm(
            @PathVariable("language") Language language,
            @PathVariable("token") String token
    ){
        Response response = new Response();
        HTTPMessageHandler messageHandler = new HTTPMessageHandler();
        userService.confirm(token, messageHandler, language.getId());
        response.put("message", messageHandler);
        return response;
    }


    @ResponseBody
    @GetMapping(value = "/images/{userId}/{language}", produces = MediaType.IMAGE_JPEG_VALUE)
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public byte[] getImage(@PathVariable("userId") User user, @PathVariable("language") Language language){
        return user.getImage();
    }

}
