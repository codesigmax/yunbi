package com.qfleaf.yunbi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfleaf.yunbi.security.authentication.provider.UnifiedAuthenticationProvider;
import com.qfleaf.yunbi.security.handler.UnifiedAccessDeniedHandler;
import com.qfleaf.yunbi.security.handler.UnifiedAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ObjectMapper objectMapper) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(// Knife4j 相关路径
                                "/doc.html/**",
                                "/webjars/**",
                                "/swagger-resources/**",
                                "/v2/api-docs/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html/**",
                                "/favicon.ico").permitAll()
                                .anyRequest().permitAll()
//                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .exceptionHandling(
                        config -> {
                            config.authenticationEntryPoint(new UnifiedAuthenticationEntryPoint(objectMapper));
                            config.accessDeniedHandler(new UnifiedAccessDeniedHandler(objectMapper));
                        }
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UnifiedAuthenticationProvider unifiedAuthenticationProvider) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(unifiedAuthenticationProvider)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
