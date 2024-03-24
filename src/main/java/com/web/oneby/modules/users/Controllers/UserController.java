package com.web.oneby.modules.users.Controllers;

import com.web.oneby.commons.DTOs.PageObject;
import com.web.oneby.commons.DTOs.SearchFilter;
import com.web.oneby.commons.Enums.HTTPMessage;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Handlers.HTTPMessageHandler;
import com.web.oneby.commons.Utils.Response;
import com.web.oneby.modules.users.DTOs.UserResponse;
import com.web.oneby.modules.users.Enums.UserRole;
import com.web.oneby.modules.users.Models.User;
import com.web.oneby.modules.users.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Response findUsers(
            @RequestHeader(value = "Current-Language", defaultValue = "ru") Language language,
            @RequestBody SearchFilter request
    ){
        Response response = new Response();
        PageObject<UserResponse> users = userService.search(request, language.getId());
        response.put("users", users);
        return response;
    }

    @ResponseBody
    @GetMapping("/{userId}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public Response getUser(
            @PathVariable("userId") User user,
            @RequestHeader(value = "Current-Language", defaultValue = "ru") Language language
    ){
        Response response = new Response();
        UserResponse userResponse = UserResponse.fromUser(user, language.getId());
        response.put("user", userResponse);
        return response;
    }

//    @ResponseBody
//    @GetMapping("/favourites/{userId}")
//    @PreAuthorize(value = "isAuthenticated()")
//    public Response getUserFavourites(
//            Authentication auth,
//            @PathVariable("userId") User user,
//            @RequestHeader(value = "Current-Language", defaultValue = "ru") Language language
//    ){
//        Response response = new Response();
//        if (Objects.equals(((User) auth.getPrincipal()).getId(), user.getId()) || ((User) auth.getPrincipal()).getRoles().contains(UserRole.ADMIN)) {
//            List<BookResponse> favourites = user.getFavourites().stream().map(book -> BookResponse.fromBook(book, language.getId())).toList();
//            response.put("favourites", favourites);
//        } else {
//            response.put("message", HTTPMessageHandler.fromHTTPMessage(HTTPMessage.ACCESS_DENIED, language.getId()));
//        }
//
//        return response;
//    }


}
