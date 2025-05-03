package com.qfleaf.yunbi.security.service;

import com.qfleaf.yunbi.dao.UserDao;
import com.qfleaf.yunbi.model.User;
import com.qfleaf.yunbi.security.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名、手机号或邮箱查找用户
        User user = userDao.findByUsernameOrPhoneOrEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new LockedException("用户未激活或被锁定");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
// todo               .authorities(user.getAuthorities())
                .accountLocked(user.getStatus() == UserStatus.LOCKED)
                .disabled(user.getStatus() == UserStatus.BLOCKED)
                .build();
    }
}
