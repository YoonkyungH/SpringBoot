package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();


        try {
            // 저장
            // 팀 하나 만들고
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            // 멤버 하나 만들고
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            team.addMember(member);

            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId());   // 영속성 컨테이너 안에 있음(1차 캐시)
            List<Member> members = findTeam.getMembers();
            
            for(Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
