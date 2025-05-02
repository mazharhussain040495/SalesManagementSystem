package com.sales.auth.config;

import com.sales.common.security.FeignInternalAuthInterceptor;
import com.sales.common.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public FeignInternalAuthInterceptor feignInternalAuthInterceptor(JwtUtil jwtUtil) {
        return new FeignInternalAuthInterceptor(jwtUtil);
    }
}
