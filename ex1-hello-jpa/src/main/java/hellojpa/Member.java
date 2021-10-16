package hellojpa;

import javax.persistence.*;

@Entity // 스프링이 뜰 때 "애는 jpa를 사용하는 애구나" 알 수 있음
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne  // Member 입장에서 멤버가 다수에 속하고 팀이 단일에 속함
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }


    public void setTeam(Team team) {
        this.team = team;
    }
}
