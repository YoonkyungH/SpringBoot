package com.example.noticeBoard.service;

import com.example.noticeBoard.domain.board.*;
import com.example.noticeBoard.domain.photo.Photo;
import com.example.noticeBoard.repository.BoardRepository;
import com.example.noticeBoard.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final PhotoRepository photoRepository;
    private final FileHandler fileHandler;

    @Transactional
    public Long create(
            BoardCreateRequestDto requestDto,
            List<MultipartFile> files
    ) throws Exception {
        // 파일 처리를 위한 Board 객체 생성
        Board board = new Board(
                requestDto.getMember(),
                requestDto.getTitle(),
                requestDto.getContent()
        );

        List<Photo> photoList = fileHandler.parseFileInfo(board, files);

        // 파일이 존재할 때만 처리
        if(!photoList.isEmpty()) {
            for(Photo photo : photoList) {
                board.addPhoto(photoRepository.save(photo));    // 파일을 DB에 저장
            }
        }

        return boardRepository.save(board).getId();
//        return boardRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public void update(
            Long id,
            BoardUpdateRequestDto requestDto,
            List<MultipartFile> files
    ) throws Exception {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        List<Photo> photoList = fileHandler.parseFileInfo(board, files);

        if(!photoList.isEmpty()) {
            for(Photo photo : photoList) {
                photoRepository.save(photo);
            }
        }

        board.update(requestDto.getTitle(), requestDto.getContent());

//        return id;
    }

    /**
     * 개별 조회
     */
    @Transactional(readOnly = true)
    public BoardResponseDto searchById(Long id, List<Long> fileId) {
        Board entity = boardRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        return new BoardResponseDto(entity, fileId);
//        return new BoardResponseDto(board);
    }

    /**
     * 전체 조회
     */
    @Transactional(readOnly = true)
    public List<Board> searchAllDesc() {
        return boardRepository.findAllByOrderByIdDesc();
//        return boardRepository.findAllDesc();
    }
//    public List<BoardListResponseDto> searchAllDesc() {
//        return boardRepository.findAllByOrderByIdDesc()
//                .stream()
//                .map(BoardListResponseDto::new)
//                .collect(Collectors.toList());
////        return boardRepository.findAllDesc().stream()
////                .map(BoardListResponseDto::new)
////                .collect(Collectors.toList());
//    }

    @Transactional
    public void delete(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
                );

        boardRepository.delete(board);
    }
}
