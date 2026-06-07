package com.Tcc.Kanban.Kaeroban.service;

import com.Tcc.Kanban.Kaeroban.dto.UserRequestDTO;
import com.Tcc.Kanban.Kaeroban.dto.UserResponseDTO;
import com.Tcc.Kanban.Kaeroban.model.User;
import com.Tcc.Kanban.Kaeroban.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponseDTO save(UserRequestDTO requestDTO) {
        User user = new User();
        user.setName(requestDTO.name());
        user.setEmail(requestDTO.email());
        user.setPasswordHash(passwordEncoder.encode(requestDTO.password()));
        return toDTO(userRepository.save(user));
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public UserResponseDTO findById(Integer id) {
        return toDTO(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
    }

    public UserResponseDTO update(Integer id, UserRequestDTO requestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setName(requestDTO.name());
        user.setEmail(requestDTO.email());
        user.setPasswordHash(passwordEncoder.encode(requestDTO.password()));
        return toDTO(userRepository.save(user));
    }

    private UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getIdUser(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    public void delete(Integer id) {
        userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}