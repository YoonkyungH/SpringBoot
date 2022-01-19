package com.example.noticeBoard.controller;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardFileVO {

    private String memberId;
    private String title;
    private String content;
    private List<MultipartFile> files;

}
