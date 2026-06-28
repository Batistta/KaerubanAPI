package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.dto.BoardInviteCreateDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.BoardInviteRedeemDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.BoardInviteResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.model.Board;
import com.Tcc.Kanban.KaerubanAPI.model.BoardInvite;
import com.Tcc.Kanban.KaerubanAPI.model.User;
import com.Tcc.Kanban.KaerubanAPI.repository.BoardInviteRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.BoardRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardInviteService {

    private final BoardRepository boardRepository;
    private final BoardInviteRepository boardInviteRepository;
    private final UserRepository userRepository;

    public BoardInviteResponseDTO generate(BoardInviteCreateDTO requestDto){
        Board board = boardRepository.findById(requestDto.boardId())
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        BoardInvite invite = new BoardInvite();
        invite.setBoard(board);
        invite.setCode(generateCode());
        invite.setExpiresAt((requestDto.expiresAt()));
        return BoardInviteResponseDTO.from(boardInviteRepository.save(invite));
    }

    public void redeem(BoardInviteRedeemDTO requestDto){
        BoardInvite invite = boardInviteRepository.findByCode(requestDto.code())
                .orElseThrow(() -> new EntityNotFoundException("Invite not found"));

        if (invite.getExpiresAt() != null && invite.getExpiresAt().isBefore(LocalDate.now())) {
            boardInviteRepository.delete(invite);
            throw new IllegalStateException("Invalid invite");
        }

        User user = userRepository.findById(requestDto.idUser())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Board board = invite.getBoard();

        if(board.getOwner().getIdUser().equals(user.getIdUser()) || board.getMembers().contains(user)){
            throw new IllegalStateException("User is already on the board");
        }

        board.getMembers().add(user);
        boardRepository.save(board);
    }

    public void delete(Integer id){
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
