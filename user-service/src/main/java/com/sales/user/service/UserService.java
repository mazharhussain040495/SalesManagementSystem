package com.sales.user.service;

import com.sales.user.exception.ResourceNotFoundException;
import com.sales.user.exception.UserAlreadyExistsException;
import com.sales.user.exception.UserNotFoundException;
import com.sales.user.model.dto.UserDTO;
import com.sales.user.model.dto.UserResponseDTO;
import com.sales.user.model.dto.UserResponseLoginDTO;
import com.sales.user.model.entity.User;
import com.sales.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @CachePut(
            value = "userByEmail",
            condition = "#userDto != null and #userDto.email() != null",
            key = "#userDto.email()"
    )
    @CacheEvict(value = "allUsers", allEntries = true)
    public UserResponseDTO registerUser(UserDTO userDto) {
        if (userRepository.findByEmail(userDto.email()).isPresent()) {
            throw new UserAlreadyExistsException("User with email already exists: " + userDto.email());
        }

        User user = User.builder()
                .name(userDto.name())
                .email(userDto.email())
                .phone(userDto.phone())
                .address(userDto.address())
                .password(passwordEncoder.encode(userDto.password()))
                .build();

        return mapToResponseDTO(userRepository.save(user));
    }


    @Cacheable(
            value = "userByEmail",
            key = "#email",
            condition = "#email != null && !#email.isEmpty()",
            unless = "#result == null"
    )
    public UserResponseDTO getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getAddress(), user.getPassword());
    }


    @Cacheable(value = "allUsers")
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Cacheable(value = "user", key = "#id",condition = "#id != null")
    public UserResponseDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToResponseDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @CachePut(value = "user", key = "#id", condition = "#id != null")
    @CacheEvict(value = "allUsers", allEntries = true)
    public UserResponseDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.setName(dto.name());
        user.setPhone(dto.phone());
        user.setAddress(dto.address());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));

        return mapToResponseDTO(userRepository.save(user));
    }

    @Caching(evict = {
            @CacheEvict(value = "user", key = "#id", condition = "#id != null"),
            @CacheEvict(value = "allUsers", allEntries = true)
    })
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getPassword()
        );
    }
}
