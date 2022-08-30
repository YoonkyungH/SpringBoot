package com.dbsrud.webservice.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
//    @GeneratedValue
    private Long id;

    private String username;
    private String userId;
    private String password;
    private int age;

    @Builder
    public Member(String username, String userId, String password, int age) {
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.age = age;
    }
}
