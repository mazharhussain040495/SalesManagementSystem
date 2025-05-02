package com.sales.user;

import com.sales.common.config.JwtProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;


@SpringBootApplication(scanBasePackages = {"com.sales.user", "com.sales.common"})
@EnableConfigurationProperties(JwtProperties.class)
@EnableCaching
public class UserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
