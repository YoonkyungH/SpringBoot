package com.example.noticeBoard.service;

import com.example.noticeBoard.domain.photo.Photo;
import com.example.noticeBoard.domain.photo.PhotoDto;
import com.example.noticeBoard.domain.photo.PhotoResponseDto;
import com.example.noticeBoard.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    /**
     * 이미지 개별 조회
     */
    @Transactional(readOnly = true)
    public PhotoDto findByFileId(Long id) {
        Photo entity = photoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 파일이 존재하지 않습니다.")
        );

        PhotoDto photoDto = PhotoDto.builder()
                .origFileName(entity.getOrigFileName())
                .filePath(entity.getFilePath())
                .fileSize(entity.getFileSize())
                .build();

        return photoDto;
    }

    /**
     * 이미지 전체 조회
     */
    @Transactional
    public List<PhotoResponseDto> findAllByBoard(Long boardId) {
        List<Photo> photoList = photoRepository.findAllByBoardId(boardId);

        return photoList.stream()
                .map(PhotoResponseDto::new)
                .collect(Collectors.toList());
    }
}
