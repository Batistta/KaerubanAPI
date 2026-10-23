package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.config.TokenService;
import com.Tcc.Kanban.KaerubanAPI.dto.user.AuthRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.user.LoginResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.user.RegisterDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.user.UserRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;

    public LoginResponseDTO login(AuthRequestDTO requestDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(requestDTO.email(),
                requestDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserDetailsImpl) auth.getPrincipal());
        var userId = userService.findByEmail(requestDTO.email()).idUser();
        return new LoginResponseDTO(token, userId);
    }

    public void register(RegisterDTO requestDTO){
        userService.save(new UserRequestDTO(requestDTO.name(), requestDTO.email(), requestDTO.password()));
    }
}