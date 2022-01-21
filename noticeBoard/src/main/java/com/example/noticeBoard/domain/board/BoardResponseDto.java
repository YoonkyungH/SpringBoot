package com.example.noticeBoard.domain.board;

import lombok.Getter;

import java.util.List;

@Getter
public class BoardResponseDto {
    private Long id;
    private String member;
    private String title;
    private String content;
    private List<Long> fileId;

//     public BoardResponseDto(Board entity) {
    public BoardResponseDto(Board entity, List<Long> fileId) {
        this.id = entity.getId();
        this.member = entity.getMember().getName();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.fileId = fileId;
    }
}
