package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.dto.BoardRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.BoardResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.UserResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.model.Board;
import com.Tcc.Kanban.KaerubanAPI.model.User;
import com.Tcc.Kanban.KaerubanAPI.repository.BoardRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardResponseDTO save(BoardRequestDTO requestDTO) {
        User owner = userRepository.findById(requestDTO.ownerId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Board board = new Board();
        board.setTitle(requestDTO.title());
        board.setColor(requestDTO.color());
        board.setOwner(owner);
        board.setMembers(List.of(owner));
        return toDTO(boardRepository.save(board));
    }

    public List<BoardResponseDTO> findAll() {
        return boardRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public BoardResponseDTO findById(Integer id) {
        return toDTO(boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Board not found")));
    }

    public List<BoardResponseDTO> findByUser(Integer userId) {
        List<Board> owned = boardRepository.findByOwner_IdUser(userId);
        List<Board> member = boardRepository.findByMembers_IdUser(userId);

        return Stream.concat(owned.stream(), member.stream())
                .distinct() // ignora duplicatas
                .map(this::toDTO)
                .toList();
    }

    public List<BoardResponseDTO> findByUserAndTitle(Integer userId, String title) {
        List<Board> owned = boardRepository.findByOwner_IdUserAndTitleContainingIgnoreCase(userId, title);
        List<Board> member = boardRepository.findByMembers_IdUserAndTitleContainingIgnoreCase(userId, title);

        return Stream.concat(owned.stream(), member.stream())
                .distinct()
                .map(this::toDTO)
                .toList();
    }

    //apenas colaborador
    public List<BoardResponseDTO> findByMember(Integer userId) {
        return boardRepository.findByMembers_IdUser(userId)
                .stream()
                .filter(board -> !board.getOwner().getIdUser().equals(userId))
                .map(this::toDTO)
                .toList();
    }

    public BoardResponseDTO update(Integer id, BoardRequestDTO requestDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        board.setTitle(requestDTO.title());
        board.setColor(requestDTO.color());
        return toDTO(boardRepository.save(board));
    }

    public void delete(Integer id) {
        boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        boardRepository.deleteById(id);
    }

    public BoardResponseDTO addMember(Integer boardId, Integer userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        board.getMembers().add(user);
        return toDTO(boardRepository.save(board));
    }

    public BoardResponseDTO removeMember(Integer boardId, Integer userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        board.getMembers().removeIf(u -> u.getIdUser().equals(userId));
        return toDTO(boardRepository.save(board));
    }

    private UserResponseDTO userToDTO(User user) {
        return new UserResponseDTO(
                user.getIdUser(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }


    private BoardResponseDTO toDTO(Board board) {
        return new BoardResponseDTO(
                board.getIdBoard(),
                board.getTitle(),
                board.getColor(),
                userToDTO(board.getOwner()),
                board.getMembers().stream().map(this::userToDTO).toList()
        );
    }
}