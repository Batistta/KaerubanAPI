package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.dto.user.PasswordChangeDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.user.UserRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.user.UserResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.user.UserUpdateDTO;
import com.Tcc.Kanban.KaerubanAPI.model.User;
import com.Tcc.Kanban.KaerubanAPI.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO save(UserRequestDTO requestDTO) {
        String email = requestDTO.email().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já em uso " + email);
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
    public UserResponseDTO findByEmail(String email){
        try{
            return toDTO(userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email)));
        } catch (RuntimeException e){
            throw new EntityNotFoundException("User not found");
        }

    }
    public UserResponseDTO update(UUID id, UserUpdateDTO requestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        String email = requestDTO.email().toLowerCase();
        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já em uso " + email);
        }
        user.setName(requestDTO.name());
        user.setEmail(requestDTO.email().toLowerCase());
        user.setPicture(requestDTO.picture());
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
                user.getPicture(),
                user.getCreatedAt()
        );
    }

    public void delete(UUID id) {
        userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
    @Transactional
    public void changePassword(UUID id, PasswordChangeDTO dto, Authentication auth) {
        if (!id.toString().equals(auth.getName())) {      // ajuste a como seu token guarda o id
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (!passwordEncoder.matches(dto.currentPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha atual incorreta");
        }
        user.setPasswordHash(passwordEncoder.encode(dto.newPassword()));
    }

}