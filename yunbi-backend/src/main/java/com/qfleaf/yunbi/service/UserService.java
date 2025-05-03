package com.qfleaf.yunbi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qfleaf.yunbi.model.User;
import com.qfleaf.yunbi.model.request.RegisterRequest;
import com.qfleaf.yunbi.security.request.UnifiedLoginRequest;
import com.qfleaf.yunbi.security.response.TokenLoginResponse;

public interface UserService extends IService<User> {
    boolean register(RegisterRequest registerRequest);

    TokenLoginResponse login(UnifiedLoginRequest unifiedLoginRequest);
}
