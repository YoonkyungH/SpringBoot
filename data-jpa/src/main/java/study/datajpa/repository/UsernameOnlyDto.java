package study.datajpa.repository;

public class UsernameOnlyDto {

    private final String username;

    public UsernameOnlyDto(String username) {   // 여기 "username" 이 파라미터 이름을 가지고 씀. 그래서 "username2" 이렇게 달라지면 안됨
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
