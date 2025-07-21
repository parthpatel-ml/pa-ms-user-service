package com.propyassistance.pa_ms_user_service.service.impl;


import com.propyassistance.pa_ms_user_service.dto.UserRequestDto;
import com.propyassistance.pa_ms_user_service.dto.UserResponseDto;
import com.propyassistance.pa_ms_user_service.entity.User;
import com.propyassistance.pa_ms_user_service.repository.UserRepository;
import com.propyassistance.pa_ms_user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = User.builder()
                .oktaId(dto.getOktaId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .role(dto.getRole())
                .status("ACTIVE")
                .build();
        return toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto getUser(UUID id) {
        return userRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    private UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }
}
