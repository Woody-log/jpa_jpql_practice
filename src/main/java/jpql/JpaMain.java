package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(1);

            em.persist(member);
            em.flush();
            em.clear();

            //TypedQuery<Member> query = em.createQuery("select m from Member m where m.username =: username", Member.class);
            //query.setParameter("username", "member1");

            // TypedQuery<String> query1 = em.createQuery("select m.username from Member m ", String.class); // 반환타입이 명확할 때
            // Query query2 = em.createQuery("select m.username, m.age from Member m ");    // 반환 타입이 명확하지 않을 때

            //List<Member> members = query.getResultList();   // 결과가 하나 이상일 때, 결과가 없을 때 빈 리스트 반환
            //Member member2 = query.getSingleResult();       // 결과가 하나일 때, 결과가 없거나, 둘이상 일때 에러 발생..


            // 거지같다 패키지명 다 적어줘야되네;
            //List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m ", MemberDTO.class).getResultList();
            //MemberDTO memberDTO = resultList.get(0);
            //System.out.println("username : " + memberDTO.getUsername());
            //System.out.println("age : " + memberDTO.getAge());

            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("result.size " + result.size() );

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
