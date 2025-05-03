package com.qfleaf.yunbi.security.authentication.token.factory;

import com.qfleaf.yunbi.security.authentication.token.PasswordAuthenticationToken;
import com.qfleaf.yunbi.security.authentication.token.EmailCodeAuthenticationToken;
import com.qfleaf.yunbi.security.authentication.token.SmsCodeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationTokenFactory {

    public Authentication createPasswordToken(String account, String password) {
        return new PasswordAuthenticationToken(account, password);
    }

    public Authentication createSmsCodeToken(String phone, String code) {
        return new SmsCodeAuthenticationToken(phone, code);
    }

    public Authentication createEmailCodeToken(String email, String code) {
        return new EmailCodeAuthenticationToken(email, code);
    }

    public Authentication createAuthenticatedToken(UserDetails userDetails, Authentication originalAuth) {
        if (originalAuth instanceof SmsCodeAuthenticationToken) {
            return new SmsCodeAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else if (originalAuth instanceof EmailCodeAuthenticationToken) {
            return new EmailCodeAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else if (originalAuth instanceof PasswordAuthenticationToken) {
            return new PasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else {
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
    }
}
