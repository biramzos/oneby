package com.web.oneby.modules.users.Controllers;

import com.web.oneby.commons.DTOs.SearchFilter;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.DTOs.Response;
import com.web.oneby.modules.users.DTOs.UserResponse;
import com.web.oneby.modules.users.Models.User;
import com.web.oneby.modules.users.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public Response search(@RequestHeader(value = "Current-Language", defaultValue = "ru") Language language, @RequestBody SearchFilter filter) {
        return Response.getResponse("users", userService.search(filter, language));
    }

    @ResponseBody
    @GetMapping("/{userId}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public Response getUser(@RequestHeader(value = "Current-Language", defaultValue = "ru") Language language, @PathVariable("userId") User user){
        return Response.getResponse("user", UserResponse.fromUser(user, language));
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
