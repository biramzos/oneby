package com.web.oneby.Controllers;

import com.web.oneby.DTO.BookResponse;
import com.web.oneby.DTO.PageObject;
import com.web.oneby.DTO.UserResponse;
import com.web.oneby.DTO.UserSearchFilterRequest;
import com.web.oneby.Enums.HTTPMessage;
import com.web.oneby.Enums.Language;
import com.web.oneby.Enums.UserRole;
import com.web.oneby.Handlers.HTTPMessageHandler;
import com.web.oneby.Models.User;
import com.web.oneby.Services.UserService;
import com.web.oneby.Utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(
            UserService userService
    ){
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping("/{pageNumber}/{countInPart}/{language}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public Response findUsers(
            @PathVariable("pageNumber") Integer pageNumber,
            @PathVariable("countInPart") Integer countInPart,
            @PathVariable("language") Language language,
            @RequestBody UserSearchFilterRequest request
    ){
        Response response = new Response();
        PageObject<UserResponse> users = userService.search(request, countInPart, pageNumber, language.getId());
        response.put("users", users);
        return response;
    }

    @ResponseBody
    @GetMapping("/{userId}/{language}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public Response getUser(
            @PathVariable("userId") User user,
            @PathVariable("language") Language language
    ){
        Response response = new Response();
        UserResponse userResponse = UserResponse.fromUser(user, language.getId());
        response.put("user", userResponse);
        return response;
    }

    @ResponseBody
    @GetMapping("/favourites/{userId}/{language}")
    @PreAuthorize(value = "isAuthenticated()")
    public Response getUserFavourites(
            Authentication auth,
            @PathVariable("userId") User user,
            @PathVariable("language") Language language
    ){
        Response response = new Response();
        if (((User) auth.getPrincipal()) == user || ((User) auth.getPrincipal()).getRoles().contains(UserRole.ADMIN)) {
            List<BookResponse> favourites = user.getFavourites().stream().map(book -> BookResponse.fromBook(book, language.getId())).toList();
            response.put("favourites", favourites);
        } else {
            response.put("message", HTTPMessageHandler.fromHTTPMessage(HTTPMessage.ACCESS_DENIED, language.getId()));
        }

        return response;
    }


}
