package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class DefaultMemberRepository implements MemberRepository {

    private final EntityManager em;

    public DefaultMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public List<Member> findByOptionalParameters(String userId, String phoneNumber) {
        return em.createQuery("SELECT m FROM Member m WHERE 1 = 1"
                + " AND (:userId is null OR m.userId = :userId)"
                + " AND (:phoneNumber is null OR m.phoneNumber = :phoneNumber)", Member.class)
                .setParameter("userId", userId)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();
    }

    @Override
    public List<Member> findByUserId(String userId) {
        return findByOptionalParameters(userId, null);
    }

    @Override
    public List<Member> findByPhoneNumber(String phoneNumber) {
        return findByOptionalParameters(null, phoneNumber);
    }
}
