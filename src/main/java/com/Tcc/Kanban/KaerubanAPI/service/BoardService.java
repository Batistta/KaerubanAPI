package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.dto.board.BoardRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.board.BoardResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.user.UserResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.model.Board;
import com.Tcc.Kanban.KaerubanAPI.model.BoardMember;
import com.Tcc.Kanban.KaerubanAPI.model.Role;
import com.Tcc.Kanban.KaerubanAPI.model.User;
import com.Tcc.Kanban.KaerubanAPI.repository.BoardRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.RoleRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public BoardResponseDTO save(BoardRequestDTO requestDTO) {
        User owner = userRepository.findById(requestDTO.ownerId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role ownerRole = roleRepository.findByTitle("owner")
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));

        Board board = new Board();
        board.setTitle(requestDTO.title());
        board.setColor(requestDTO.color());

        BoardMember member = new BoardMember();
        member.setUser(owner);
        member.setBoard(board);
        member.setRole(ownerRole);
        board.setMembers(List.of(member));

        return toDTO(boardRepository.save(board));
    }

    public List<BoardResponseDTO> findAll() {
        return boardRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public BoardResponseDTO findById(UUID id) {
        return toDTO(boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Board not found")));
    }

    public List<BoardResponseDTO> findByUser(UUID userId) {
        return boardRepository.findByMembers_User_Id(userId)
                .stream().map(this::toDTO).toList();
    }

    public List<BoardResponseDTO> findByUserAndTitle(UUID userId, String title) {
        return boardRepository.findByMembers_User_IdAndTitleContainingIgnoreCase(userId, title)
                .stream().map(this::toDTO).toList();
    }

    //apenas colaborador
    public List<BoardResponseDTO> findByMember(UUID userId) {
        return boardRepository.findByMembers_User_Id(userId).stream()
                .filter(board -> board.getMembers().stream()
                        .anyMatch(m -> m.getUser().getId().equals(userId)
                                && !m.getRole().getTitle().equals("owner")))
                .map(this::toDTO).toList();
    }

    public BoardResponseDTO update(UUID id, BoardRequestDTO requestDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        board.setTitle(requestDTO.title());
        board.setColor(requestDTO.color());
        return toDTO(boardRepository.save(board));
    }

    public void delete(UUID id) {
        boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        boardRepository.deleteById(id);
    }

    public BoardResponseDTO addMember(UUID boardId, UUID userId, String roleTitle) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepository.findByTitle(roleTitle)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));

        BoardMember member = new BoardMember();
        member.setBoard(board);
        member.setUser(user);
        member.setRole(role);
        board.getMembers().add(member);

        return toDTO(boardRepository.save(board));
    }

    public BoardResponseDTO removeMember(UUID boardId, UUID userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        board.getMembers().removeIf(m -> m.getUser().getId().equals(userId));
        return toDTO(boardRepository.save(board));
    }

    private UserResponseDTO userToDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt());
    }

    private BoardResponseDTO toDTO(Board board) {
        User owner = board.getMembers().stream()
                .filter(m -> m.getRole().getTitle().equals("owner"))
                .findFirst().map(BoardMember::getUser)
                .orElseThrow(() -> new IllegalStateException("Board sem owner"));

        return new BoardResponseDTO(
                board.getId(), board.getTitle(), board.getColor(),
                userToDTO(owner),
                board.getMembers().stream().map(m -> userToDTO(m.getUser())).toList()
        );
    }
}