package com.qfleaf.yunbi.security.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenLoginResponse {
    private String token;
}
