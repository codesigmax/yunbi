package com.qfleaf.yunbi.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qfleaf.yunbi.dao.mapper.UserMapper;
import com.qfleaf.yunbi.model.User;
import com.qfleaf.yunbi.model.request.RegisterRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final UserMapper userMapper;

    @Getter
    @RequiredArgsConstructor
    public enum ExistType {
        USERNAME("用户名已被使用"),
        EMAIL("邮箱已被使用"),
        PHONE("手机号已被使用");

        final String msg;
    }

    public Optional<User> findByUsernameOrPhoneOrEmail(String account) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(User::getUsername, account)
                .or()
                .eq(User::getPhone, account)
                .or()
                .eq(User::getEmail, account);
        return Optional.ofNullable(userMapper.findByUsernameOrPhoneOrEmail(wrapper));
    }

    // region exist
    public ExistType exist(RegisterRequest register) {
        return existByUsername(register.getUsername()) ?
                ExistType.USERNAME : existByEmail(register.getEmail()) ?
                ExistType.EMAIL : existByPhone(register.getPhone()) ?
                ExistType.PHONE : null;
    }

    public boolean existByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return userMapper.exists(wrapper);
    }

    public boolean existByEmail(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        return userMapper.exists(wrapper);
    }

    public boolean existByPhone(String phone) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        return userMapper.exists(wrapper);
    }
    // endregion

    public boolean save(User entity) {
        int insert = userMapper.insert(entity);
        return insert > 0;
    }
}
