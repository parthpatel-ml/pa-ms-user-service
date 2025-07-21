package com.propyassistance.pa_ms_user_service.service;

import com.propyassistance.pa_ms_user_service.dto.UserRequestDto;
import com.propyassistance.pa_ms_user_service.dto.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);
    UserResponseDto getUser(UUID id);
    List<UserResponseDto> getAllUsers();
    void deleteUser(UUID id);
}
