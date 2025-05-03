package com.qfleaf.yunbi.security.authentication.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class EmailCodeAuthenticationToken extends AbstractAuthenticationToken {
    
    private final Object principal;  // 这里存储邮箱地址
    private Object credentials;     // 验证码
    
    public EmailCodeAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }
    
    public EmailCodeAuthenticationToken(Object principal, 
                                      Object credentials,
                                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }
    
    @Override
    public Object getCredentials() {
        return this.credentials;
    }
    
    @Override
    public Object getPrincipal() {
        return this.principal;
    }
    
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }
    
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
