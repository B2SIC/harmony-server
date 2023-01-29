package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
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
    public Optional<Member> findByOptionalParameters(String userId, String phoneNumber) {
        List<Member> resultList = em.createQuery("SELECT m FROM Member m WHERE 1 = 1"
                        + " AND (:userId is null OR m.userId = :userId)"
                        + " AND (:phoneNumber is null OR m.phoneNumber = :phoneNumber)", Member.class)
                .setParameter("userId", userId)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();

        if (resultList.size() >= 2){
            log.error("ResultList has {} items", resultList.size());
        }
        return resultList.stream().findAny();
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        return findByOptionalParameters(userId, null);
    }

    @Override
    public Optional<Member> findByPhoneNumber(String phoneNumber) {
        return findByOptionalParameters(null, phoneNumber);
    }
}
