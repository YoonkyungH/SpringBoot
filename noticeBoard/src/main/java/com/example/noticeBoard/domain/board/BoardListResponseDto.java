package com.example.noticeBoard.domain.board;

import lombok.Getter;

@Getter
public class BoardListResponseDto {
    private Long id;
    private String member;
    private String title;
    private Long thumbnailId;   // 썸네일 id

    public BoardListResponseDto(Board entity) {
        this.id = entity.getId();
        this.member = entity.getMember().getName();
        this.title = entity.getTitle();

        if(!entity.getPhoto().isEmpty())  // 첨부파일 존재 o
            this.thumbnailId = entity.getPhoto().get(0).getId();  // 첫번째 이미지 반환
        else // 첨부파일 존재 x
            this.thumbnailId = 0L;  // 서버에 저장된 기본 이미지 반환
    }
}
