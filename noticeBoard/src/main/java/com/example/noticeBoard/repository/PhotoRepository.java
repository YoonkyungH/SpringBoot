package com.example.noticeBoard.repository;

import com.example.noticeBoard.domain.photo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findAllByBoardId(Long boardId);
}
