package com.qfleaf.yunbi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfleaf.yunbi.common.ResultCode;
import com.qfleaf.yunbi.common.exception.BusinessException;
import com.qfleaf.yunbi.dao.UserDao;
import com.qfleaf.yunbi.dao.mapper.UserMapper;
import com.qfleaf.yunbi.model.User;
import com.qfleaf.yunbi.model.converter.UserConverter;
import com.qfleaf.yunbi.model.request.RegisterRequest;
import com.qfleaf.yunbi.security.authentication.token.factory.AuthenticationTokenFactory;
import com.qfleaf.yunbi.security.enums.LoginType;
import com.qfleaf.yunbi.security.jwt.JwtTokenProvider;
import com.qfleaf.yunbi.security.request.UnifiedLoginRequest;
import com.qfleaf.yunbi.security.response.TokenLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationTokenFactory authenticationTokenFactory;
    private final UserDao userDao;
    private final UserConverter userConverter;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean register(RegisterRequest registerRequest) {
        UserDao.ExistType exist = userDao.exist(registerRequest);
        if (exist != null) {
            throw new BusinessException(ResultCode.CONFLICT.getCode(), exist.getMsg());
        }
        User entity = userConverter.toEntity(registerRequest);
        return userDao.save(entity);
    }

    @Override
    public TokenLoginResponse login(UnifiedLoginRequest unifiedLoginRequest) {
        Authentication token = null;
        // todo refactor
        LoginType loginType = unifiedLoginRequest.getType();
        if (loginType == LoginType.PASSWORD) {
            token = authenticationTokenFactory.createPasswordToken(unifiedLoginRequest.getAccount(), unifiedLoginRequest.getPassword());
        } else if (loginType == LoginType.EMAIL) {
            token = authenticationTokenFactory.createEmailCodeToken(unifiedLoginRequest.getAccount(), unifiedLoginRequest.getCode());
        } else if (loginType == LoginType.PHONE) {
            token = authenticationTokenFactory.createSmsCodeToken(unifiedLoginRequest.getAccount(), unifiedLoginRequest.getCode());
        }
        Authentication authenticate = authenticationManager.authenticate(token);
        UserDetails principal = (UserDetails) authenticate.getPrincipal();
        String access = jwtTokenProvider.generateAccessToken(principal);
        return TokenLoginResponse.builder()
                .token(access)
                .build();
    }
}
