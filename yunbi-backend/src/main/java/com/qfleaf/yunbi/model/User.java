package com.qfleaf.yunbi.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qfleaf.yunbi.security.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("users")
public class User {
    @TableId
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String password;
    private java.sql.Timestamp createTime;
    private java.sql.Timestamp updateTime;
    private UserStatus status;
}
