package com.example.noticeBoard.repository;

import com.example.noticeBoard.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByOrderByIdDesc();
}
