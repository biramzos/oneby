package com.web.oneby.commons.DTOs;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.Authentication;

@Data
@Builder
public class TokenData {
    private String accessToken;
    private String refreshToken;
}
