package com.web.oneby.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserSearchFilterRequest {
    private String name;
    private List<String> roles;


}
