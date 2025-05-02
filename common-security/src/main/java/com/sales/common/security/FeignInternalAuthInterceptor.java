package com.sales.common.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignInternalAuthInterceptor implements RequestInterceptor {

    private final JwtUtil jwtUtil;

    public FeignInternalAuthInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void apply(RequestTemplate template) {
        String token = jwtUtil.generateInternalToken();
        template.header("X-INTERNAL-TOKEN", token);
    }
}
