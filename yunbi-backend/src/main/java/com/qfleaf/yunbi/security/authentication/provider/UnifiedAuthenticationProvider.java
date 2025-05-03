package com.qfleaf.yunbi.security.authentication.provider;

import com.qfleaf.yunbi.common.ResultCode;
import com.qfleaf.yunbi.common.exception.BusinessException;
import com.qfleaf.yunbi.security.authentication.token.EmailCodeAuthenticationToken;
import com.qfleaf.yunbi.security.authentication.token.PasswordAuthenticationToken;
import com.qfleaf.yunbi.security.authentication.token.SmsCodeAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnifiedAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof PasswordAuthenticationToken) {
            return processPasswordAuthentication((PasswordAuthenticationToken) authentication);
        } else if (authentication instanceof SmsCodeAuthenticationToken) {
            return processSmsCodeAuthentication((SmsCodeAuthenticationToken) authentication);
        } else if (authentication instanceof EmailCodeAuthenticationToken) {
            return processEmailCodeAuthentication((EmailCodeAuthenticationToken) authentication);
        }
        throw new AuthenticationServiceException("不支持的认证类型");
    }

    private Authentication processPasswordAuthentication(PasswordAuthenticationToken authentication) {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        return new PasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
    }

    private Authentication processSmsCodeAuthentication(SmsCodeAuthenticationToken authentication) {
        String phone = (String) authentication.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
        // todo 校验验证码
//        return new SmsCodeAuthenticationToken(
//                userDetails, null, userDetails.getAuthorities());
        throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "暂不支持sms登陆");
    }

    private Authentication processEmailCodeAuthentication(EmailCodeAuthenticationToken authentication) {
        String email = (String) authentication.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        // todo 校验验证码
//        return new EmailCodeAuthenticationToken(
//                userDetails, null, userDetails.getAuthorities());
        throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "暂不支持邮箱验证码登陆");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
