package com.sales.common.config;

import com.sales.common.config.JwtProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class CommonSecurityAutoConfiguration {
    @PostConstruct
    public void init() {
        System.out.println("CommonSecurityAutoConfiguration loaded!");
    }

}
