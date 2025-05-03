package com.qfleaf.yunbi.security.request;

import com.qfleaf.yunbi.security.enums.LoginType;
import lombok.Data;

@Data
public class UnifiedLoginRequest {
    private LoginType type;      // 登录类型
    private String account;    // 账号(用户名/手机号/邮箱)
    private String password;   // 密码(type=1时使用)
    private String code;       // 验证码(type=2,3时使用)
}