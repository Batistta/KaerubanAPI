package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.AuthRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.UserResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody AuthRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.login(requestDTO));
    }
}
