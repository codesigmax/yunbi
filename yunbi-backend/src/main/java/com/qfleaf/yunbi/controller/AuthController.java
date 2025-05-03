package com.qfleaf.yunbi.controller;

import com.qfleaf.yunbi.common.ApiResponse;
import com.qfleaf.yunbi.model.request.RegisterRequest;
import com.qfleaf.yunbi.security.request.UnifiedLoginRequest;
import com.qfleaf.yunbi.security.response.TokenLoginResponse;
import com.qfleaf.yunbi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "鉴权API")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<Boolean> register(@Valid @RequestBody RegisterRequest registerRequest) {
        boolean register = userService.register(registerRequest);
        return ApiResponse.success(register);
    }

    @PostMapping("/login")
    public ApiResponse<TokenLoginResponse> login(@Valid @RequestBody UnifiedLoginRequest loginRequest) {
        TokenLoginResponse login = userService.login(loginRequest);
        return ApiResponse.success(login);
    }
}