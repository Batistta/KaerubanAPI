package com.Tcc.Kanban.Kaeroban.service;

import com.Tcc.Kanban.Kaeroban.dto.AuthRequestDTO;
import com.Tcc.Kanban.Kaeroban.dto.UserResponseDTO;
import com.Tcc.Kanban.Kaeroban.model.User;
import com.Tcc.Kanban.Kaeroban.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponseDTO login(AuthRequestDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.email())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!passwordEncoder.matches(requestDTO.password(), user.getPasswordHash())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        return new UserResponseDTO(
                user.getIdUser(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}