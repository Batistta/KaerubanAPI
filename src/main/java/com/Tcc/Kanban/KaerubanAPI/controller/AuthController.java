package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.user.AuthRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.user.RegisterDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.user.UserRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.user.UserResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AuthRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.login(requestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterDTO requestDTO){
        authService.register(requestDTO);
        return ResponseEntity.ok().build();
    }

}
