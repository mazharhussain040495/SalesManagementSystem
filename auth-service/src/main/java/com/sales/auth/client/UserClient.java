package com.sales.auth.client;

import com.sales.auth.config.FeignClientConfig;
import com.sales.auth.model.dto.UserDTO;
import com.sales.auth.model.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "http://localhost:8083" ,configuration = FeignClientConfig.class)
//@FeignClient(name = "user-service", url = "http://user-service:8083" ,configuration = FeignClientConfig.class)

public interface UserClient {

    @PostMapping("/api/users/")
    UserResponseDTO registerUser(@RequestBody UserDTO user);

    @GetMapping("/api/users/email/{email}")
    UserResponseDTO getUserByEmail(@PathVariable("email") String email);
}
