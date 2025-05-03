package com.qfleaf.yunbi.security.authentication.token;
// 直接使用Spring Security内置的UsernamePasswordAuthenticationToken
// 不需要额外实现，但我们可以创建定制版本增加业务字段

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class PasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public PasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public PasswordAuthenticationToken(Object principal, Object credentials,
                                       Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}


