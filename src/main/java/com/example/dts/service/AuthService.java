package com.example.dts.service;

import com.example.dts.dto.AuthResponse;
import com.example.dts.dto.LoginRequest;
import com.example.dts.dto.UserDto;
import com.example.dts.entity.User;
import com.example.dts.repository.UserRepository;
import com.example.dts.security.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider,
                       UserRepository userRepository,
                       ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication.getName());

        User user = userRepository.findByUsernameAndDeletedFalse(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDto userDto = modelMapper.map(user, UserDto.class);

        return new AuthResponse(jwt, refreshToken, userDto);
    }

    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        User user = userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newToken = jwtTokenProvider.generateTokenFromUsername(username);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(username);

        UserDto userDto = modelMapper.map(user, UserDto.class);

        return new AuthResponse(newToken, newRefreshToken, userDto);
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAvatar(user.getAvatar());
        dto.setStatus(user.getStatus());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
