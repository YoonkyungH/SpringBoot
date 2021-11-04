package study.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;

    protected Member() {    // entity는 기본적으로 (파라미터가 없는)기본 생성자가 필요함
    }

    public Member(String username) {
        this.username = username;
    }
}
