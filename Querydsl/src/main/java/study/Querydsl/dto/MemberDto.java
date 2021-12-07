package study.Querydsl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // 기본 생성자 대신
public class MemberDto {

    private String username;
    private int age;

//    public MemberDto() {  // @NoArgsConstructor로 대신할 수 있음
//    }

    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
