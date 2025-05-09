package com.qfleaf.yunbi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@RequiredArgsConstructor
public class OpenAIConfig {
    private final ChatClient.Builder builder;

    @Bean
    public ChatClient chatClient() {
        Resource text = new ClassPathResource("prompt.md");
        return builder
                .defaultSystem(text)
                .build();
    }
}
