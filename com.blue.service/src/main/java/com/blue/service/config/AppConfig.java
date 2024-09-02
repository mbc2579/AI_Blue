package com.blue.service.config;

import com.blue.service.domain.product.DescriptionGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public DescriptionGenerator descriptionGenerator() {
        return new DescriptionGenerator();
    }
}
