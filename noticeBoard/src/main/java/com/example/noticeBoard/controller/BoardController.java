package com.example.noticeBoard.controller;

import com.example.noticeBoard.domain.board.*;
import com.example.noticeBoard.domain.member.Member;
import com.example.noticeBoard.domain.photo.Photo;
import com.example.noticeBoard.domain.photo.PhotoDto;
import com.example.noticeBoard.domain.photo.PhotoResponseDto;
import com.example.noticeBoard.service.BoardService;
import com.example.noticeBoard.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final PhotoService fileService;
    private final MemberService memberService;

    @PostMapping("/board")
    public Long create(@RequestBody BoardCreateRequestDto requestDto) {
        return boardService.create(requestDto);
    }

//    @PutMapping("/board/{id}")
//    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto requestDto) {
//        return boardService.update(id, requestDto);
//    }

    @PutMapping("/board/{id}")
    public Long update(@PathVariable Long id, BoardFileVO boardFileVO) throws Exception {

        BoardUpdateRequestDto requestDto =
                BoardUpdateRequestDto
                        .builder()
                        .title(boardFileVO.getTitle())
                        .content(boardFileVO.getContent())
                        .build();

        // DB에 저장된 파일 불러오기
        List<Photo> dbPhotoList = fileService.findAllByBoard(id);

        // 전달받은 파일
        List<MultipartFile> multipartList = boardFileVO.getFiles();

        // 새롭게 전달되어온 파일들의 목록을 저장할 List
        List<MultipartFile> addFileList = new ArrayList<>();

        if(CollectionUtils.isEmpty(dbPhotoList)) {  // DB에 아예 존재하지 않는다면
            if(!CollectionUtils.isEmpty(multipartList)) {   // 전달받은 파일이 하나라도 존재한다면
                for(MultipartFile multipartFile : multipartList)
                    addFileList.add(multipartFile); // 저장할 파일 목록에 추가해줌
            }
        } else {    // DB에 한 장 이상 존재한다면
            // DB에 저장된 파일 원본명 목록
            List<String> dbOriginNameList = new ArrayList<>();

            // DB 파일 원본명 추출
            for(Photo dbPhoto : dbPhotoList) {
                // file id로 DB에 저장된 파일 정보 가져오기
                PhotoDto dbPhotoDto = fileService.findByFileId(dbPhoto.getId());
                // DB 파일 원본명 가져오기
                String dbOrigFileName = dbPhotoDto.getOrigFileName();

                if(!multipartList.contains(dbOrigFileName)) {   // 서버에 저장된 파일 중 전달받은 파일이 존재하지 않을 때
                    fileService.deletePhoto(dbPhoto.getId());   // 파일 삭제
                } else {
                    dbOriginNameList.add(dbOrigFileName);   // DB에 저장할 파일 목록에 추가하기
                }
            }

            for(MultipartFile multipartFile : multipartList) {  // 전달받은 파일을 하나씩 검사
                // 파일 원본명 얻기
                String multipartOrigName = multipartFile.getOriginalFilename();
                if(!dbOriginNameList.contains(multipartOrigName)) { // DB에 없는 파일일 때
                    addFileList.add(multipartFile);     // DB에 저장할 파일 목록에 추가하기
                }
            }
        }

        // 각각의 인자로 게시글의 id, 수정할 정보, DB에 저장할 파일 목록 넘겨주기
        return boardService.update(id, requestDto, addFileList);
    }

    //개별 조회
    @GetMapping("/board/{id}")
    public BoardResponseDto searchById(@PathVariable Long id) {
        // 게시글 id로 해당 게시글 첨부파일 전체 조회
        List<PhotoResponseDto> photoResponseDtoList = fileService.findAllByBoard(id);
        // 게시글 첨부파일 id 담을 List 객체 생성
        List<Long> photoId = new ArrayList<>();
        // 각 첨부파일 id 추가
        for(PhotoResponseDto photoResponseDto : photoResponseDtoList)
            photoId.add(photoResponseDto.getFileId());

        // 게시글 id와 첨부파일 id 목록을 전달받아 결과 반환
        return boardService.searchById(id, photoId);
    }

    //전체 조회(목록)
    @GetMapping("/board")
    public List<BoardListResponseDto> searchAllDesc() {
        // 게시글 전체 조회
        List<Board> boardList = boardService.searchAllDesc();
//        List<Board> boardList = itemService.searchAllDesc();
        // 반환할 List<BoardListResponseDto> 생성
        List<BoardListResponseDto> responseDtoList = new ArrayList<>();

        for(Board board : boardList) {
            // 전체 조회하여 획득한 각 게시글 객체를 이용해 BoardListResponseDto 생성
            BoardListResponseDto responseDto = new BoardListResponseDto(board);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
//        return boardService.searchAllDesc();
    }

    @DeleteMapping("/board/{id}")
    public void delete(@PathVariable Long id) {
        boardService.delete(id);
    }

    @PostMapping("/board")
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(BoardFileVO boardFileVO) throws Exception {
        // Member id로 조회하는 메소드가 존재한다는 가정
        Member member = memberService.searchMemberById(
                Long.parseLong(boardFileVO.getMemberId())
        );
//        Member member = memberService.searchMemberById(
//                Long.parseLong(boardFileVO.getId())
//        );

        BoardCreateRequestDto requestDto =
                BoardCreateRequestDto
                        .builder()
                        .member(member)
                        .title(boardFileVO.getTitle())
                        .content(boardFileVO.getContent())
                        .build();

        return boardService.create(requestDto, boardFileVO.getFiles());
    }
}
