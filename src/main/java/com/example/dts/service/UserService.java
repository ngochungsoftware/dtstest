package com.example.dts.service;

import com.example.dts.dto.CreateUserRequest;
import com.example.dts.dto.UpdateUserRequest;
import com.example.dts.dto.UserDto;
import com.example.dts.entity.User;
import com.example.dts.entity.UserRole;
import com.example.dts.entity.UserStatus;
import com.example.dts.exception.ResourceNotFoundException;
import com.example.dts.exception.UserAlreadyExistsException;
import com.example.dts.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto createUser(CreateUserRequest  createUserRequest) {
        if (userRepository.existsByUsernameAndDeletedFalse(createUserRequest.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + createUserRequest.getUsername());
        }

        if (userRepository.existsByEmailAndDeletedFalse(createUserRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + createUserRequest.getEmail());
        }

        User user = modelMapper.map(createUserRequest, User.class);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setStatus(UserStatus.ACTIVE);
//        user.setRole(UserRole.USER);

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + id));
        if (user.getDeleted()) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        return modelMapper.map(user, UserDto.class);
    }

    public Page<UserDto> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAllActive(pageable);
        return users.map(user -> modelMapper.map(user, UserDto.class));
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAllActive();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto updateUser(Long id, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (user.getDeleted()) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        if (updateUserRequest.getUsername() != null && !updateUserRequest.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsernameAndDeletedFalse(updateUserRequest.getUsername())) {
                throw new UserAlreadyExistsException("Username already exists: " + updateUserRequest.getUsername());
            }
            user.setUsername(updateUserRequest.getUsername());
        }

        if (updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmailAndDeletedFalse(updateUserRequest.getEmail())) {
                throw new UserAlreadyExistsException("Email already exists: " + updateUserRequest.getEmail());
            }
            user.setEmail(updateUserRequest.getEmail());
        }

        if (updateUserRequest.getName() != null) {
            user.setName(updateUserRequest.getName());
        }
        if (updateUserRequest.getPhone() != null) {
            user.setPhone(updateUserRequest.getPhone());
        }
        if (updateUserRequest.getAvatar() != null) {
            user.setAvatar(updateUserRequest.getAvatar());
        }

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }


    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return modelMapper.map(user, UserDto.class);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (user.getDeleted()) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        user.softDelete();
        userRepository.save(user);
    }

    public void restoreUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getDeleted()) {
            throw new IllegalArgumentException("User is not deleted");
        }

        user.restore();
        userRepository.save(user);
    }

    public void changeUserStatus(Long id, UserStatus status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (user.getDeleted()) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        user.setStatus(status);
        userRepository.save(user);
    }

    public void changeUserRole(Long id, UserRole role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (user.getDeleted()) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        user.setRole(role);
        userRepository.save(user);
    }

    public Page<UserDto> searchUsers(String searchTerm, Pageable pageable) {
        Page<User> users = userRepository.searchUsers(searchTerm, pageable);
        return users.map(user -> modelMapper.map(user, UserDto.class));
    }

    public List<UserDto> getUsersByStatus(UserStatus status) {
        List<User> users = userRepository.findByStatusAndDeletedFalse(status);
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersByRole(String role) {
        List<User> users = userRepository.findByRoleAndDeletedFalse(role);
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public long getActiveUserCount() {
        return userRepository.countActiveUsers();
    }

    public List<UserDto> getDeletedUsers() {
        List<User> users = userRepository.findAllDeleted();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

}
