package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class JpaBaseEntity  {

    @Column(updatable = false)  // 생성일자는 업데이트되지 않도록
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist                 // persist하기 전에 이벤트 발생
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;      // 쿼리할 때 null이 있으면 좀 애매해서 채워두는 것
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
