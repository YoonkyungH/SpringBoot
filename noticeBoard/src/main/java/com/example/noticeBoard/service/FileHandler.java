package com.example.noticeBoard.service;

import com.example.noticeBoard.domain.board.Board;
import com.example.noticeBoard.domain.photo.Photo;
import com.example.noticeBoard.domain.photo.PhotoDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandler {

    private final PhotoService photoService;

    public FileHandler(PhotoService photoService) {
        this.photoService = photoService;
    }

    public List<Photo> parseFileInfo(
            Board board,    // Board에 존재하는 파일인지 확인하기 위함
            List<MultipartFile> multipartFiles
    )    throws Exception {
        List<Photo> fileList = new ArrayList<>();   // 반환할 파일 리스트

        // 전달되어 온 파일이 존재할 경우
        if(!CollectionUtils.isEmpty(multipartFiles)) {
            // 파일명을 업로드 한 날짜로 변경
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            // 프로젝트 디렉토리 내 저장으리 위한 절대 경로 설정
            // 경로 구분자 File.separator
            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            // 파일 저장할 세부 경로 지정
            String path = "images" + File.separator + current_date;
            File file = new File(path);

            // 존재하지 않는 디렉토리일 때
            if(!file.exists()) {
                boolean wasSuccessful = file.mkdirs();

                if(!wasSuccessful)
                    System.out.println("file: was not successful");
            }

            // 다중 파일 처리
            for(MultipartFile multipartFile : multipartFiles) {
                // 파일 확장자 추출
                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                // 확장자명이 존재하지 않을 경우 처리하지 않음
                if(ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {    // 확장자가 jpeg, png인 파일들만 받아 처리
                    if(contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if(contentType.contains("image/png"))
                        originalFileExtension = ".png";
                    else    // 다른 확장자일 경우 처리하지 않음
                        break;
                }

                // 파일명 중복을 피하기 위해 나노초까지 고려
                String new_file_name = System.nanoTime() + originalFileExtension;

                // 파일 DTO 생성
                PhotoDto photoDto = PhotoDto.builder()
                        .origFileName(multipartFile.getOriginalFilename())
                        .filePath(path + File.separator + new_file_name)
                        .fileSize(multipartFile.getSize())
                        .build();

                // 파일 DTO를 이용해 Photo 엔티티 생성
                Photo photo = new Photo(
                        photoDto.getOrigFileName(),
                        photoDto.getFilePath(),
                        photoDto.getFileSize()
                );

                // 게시글에 존재하지 않는다면, 게시글에 사진 정보 저장
                if(board.getId() != null)
                    photo.setBoard(board);
//                    photo.setItem(item);

                // 생성 후 리스트에 추가
                fileList.add(photo);

                // 업로드 한 파일 데이터를 지정한 파일에 저장
                file = new File(absolutePath + path + File.separator + new_file_name);
                multipartFile.transferTo(file);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);
            }
        }

        return fileList;
    }
}
