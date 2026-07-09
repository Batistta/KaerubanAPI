package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.dto.invite.BoardInviteCreateDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.invite.BoardInviteRedeemDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.invite.BoardInviteResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.model.*;
import com.Tcc.Kanban.KaerubanAPI.repository.BoardInviteRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.BoardRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.RoleRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardInviteService {

    private final BoardRepository boardRepository;
    private final BoardInviteRepository boardInviteRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<BoardInviteResponseDTO> getAllInvitesByBoard(UUID id){
        return boardInviteRepository.findByBoard_Id(id).stream()
                .map(BoardInviteResponseDTO::from).toList();
    }

    public BoardInviteResponseDTO generate(BoardInviteCreateDTO requestDto){
        Board board = boardRepository.findById(requestDto.boardId())
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        Role role = roleRepository.findByTitle(requestDto.roleTitle())
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        BoardInvite invite = new BoardInvite();
        invite.setBoard(board);
        invite.setRole(role);
        invite.setCode(generateCode());
        invite.setExpiresAt(requestDto.expiresAt());
        return BoardInviteResponseDTO.from(boardInviteRepository.save(invite));
    }

    @Transactional
    public void redeem(BoardInviteRedeemDTO requestDto){
        BoardInvite invite = boardInviteRepository.findByCode(requestDto.code())
                .orElseThrow(() -> new EntityNotFoundException("Invite not found"));
        if (invite.getExpiresAt() != null && invite.getExpiresAt().isBefore(LocalDate.now())) {
            boardInviteRepository.delete(invite);
            throw new IllegalStateException("Invite expired");
        }
        User user = userRepository.findById(requestDto.idUser())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Board board = invite.getBoard();
        boolean alreadyMember = board.getMembers().stream()
                .anyMatch(m -> m.getUser().getId().equals(user.getId()));
        if (alreadyMember) {
            throw new IllegalStateException("User is already on the board");
        }
        BoardMember member = new BoardMember();
        member.setBoard(board);
        member.setUser(user);
        member.setRole(invite.getRole());
        board.getMembers().add(member);
        boardRepository.save(board);
    }

    public void delete(UUID id){
        boardInviteRepository.deleteById(id);
    }

    private String generateCode() {
        String code;
        do {
            code = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        } while (boardInviteRepository.existsByCode(code));
        return code;
    }
}
