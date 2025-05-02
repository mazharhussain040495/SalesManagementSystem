package com.sales.auth;

import com.sales.common.config.CommonSecurityAutoConfiguration;
import com.sales.common.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;


@EnableFeignClients(basePackages = "com.sales.auth.client")
@SpringBootApplication(scanBasePackages = {"com.sales.auth", "com.sales.common"})
//@Import(CommonSecurityAutoConfiguration.class)
//@EnableConfigurationProperties(JwtProperties.class)
@ConfigurationPropertiesScan(basePackages = "com.sales.common.config")
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
