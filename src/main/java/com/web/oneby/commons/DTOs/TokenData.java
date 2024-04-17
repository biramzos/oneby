package com.web.oneby.commons.DTOs;

import lombok.Data;

@Data
public class TokenData {
    private String accessToken;
    private String refreshToken;
}
