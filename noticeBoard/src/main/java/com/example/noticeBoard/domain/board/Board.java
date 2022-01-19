package com.example.noticeBoard.domain.board;

import com.example.noticeBoard.domain.BaseTimeEntity;
import com.example.noticeBoard.domain.member.Member;
import com.example.noticeBoard.domain.photo.Photo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "board")
public class Board extends BaseTimeEntity {

    /**
     * 필드
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Member.class)
    @JoinColumn(name = "member_id", updatable = false)
    @JsonBackReference
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToMany(
            mappedBy = "board",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Photo> photo = new ArrayList<>();

    @Builder
    public Board(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Board에서 파일 처리
    public void addPhoto(Photo photo) {
        this.photo.add(photo);

        // 게시글에 파일이 저장되어 있지 않은 경우
        if(photo.getBoard() != this) {
            photo.setBoard(this);   // 파일 저장
        }
    }
}
