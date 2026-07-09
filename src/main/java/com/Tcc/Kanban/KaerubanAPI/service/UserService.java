package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.dto.user.UserRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.user.UserResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.user.UserUpdateDTO;
import com.Tcc.Kanban.KaerubanAPI.model.User;
import com.Tcc.Kanban.KaerubanAPI.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO save(UserRequestDTO requestDTO) {
        if (userRepository.existsByEmailIgnoreCase(requestDTO.email())){
            throw new IllegalArgumentException("Email already in use");
        }
        User user = new User();
        user.setName(requestDTO.name());
        user.setEmail(requestDTO.email().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(requestDTO.password()));
        return toDTO(userRepository.save(user));
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public UserResponseDTO findById(UUID id) {
        return toDTO(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
    }

    public UserResponseDTO update(UUID id, UserUpdateDTO requestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (!user.getEmail().equals(requestDTO.email().toLowerCase())
                && userRepository.existsByEmailIgnoreCase(requestDTO.email())) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setName(requestDTO.name());
        user.setEmail(requestDTO.email().toLowerCase());
        if (requestDTO.password() != null && !requestDTO.password().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(requestDTO.password()));
        }
        return toDTO(userRepository.save(user));
    }

    private UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    public void delete(UUID id) {
        userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}