package com.web.oneby.Controllers;

import com.web.oneby.DTO.PageObject;
import com.web.oneby.DTO.UserResponse;
import com.web.oneby.DTO.UserSearchFilterRequest;
import com.web.oneby.Enums.Language;
import com.web.oneby.Services.UserService;
import com.web.oneby.Utils.Response;
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
    @PostMapping("/{pageNumber}/{countInPart}/{language}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public Response findPageableUsers(
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


}
