package com.propyassistance.pa_ms_user_service.controller;


import com.propyassistance.pa_ms_user_service.dto.UserRequestDto;
import com.propyassistance.pa_ms_user_service.dto.UserResponseDto;
import com.propyassistance.pa_ms_user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping
    public UserResponseDto create(@Valid @RequestBody UserRequestDto dto) {
        return userService.createUser(dto);
    }

    @GetMapping("/{id}")
    public UserResponseDto get(@PathVariable UUID id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("User Service is UP!");
    }
}
