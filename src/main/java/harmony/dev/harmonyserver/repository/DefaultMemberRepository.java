package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.domain.Member;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;

import java.util.List;
import java.util.Optional;

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

    @Accessors(fluent = true, chain = true)
    @Setter
    public static class FindBy implements MemberRepository.FindBy {
        private final EntityManager em;

        private String userId;
        private String password;
        private String phoneNumber;

        public FindBy(EntityManager em) {
            this.em = em;
        }

        private List<Member> getResult() {
            return em.createQuery("SELECT m FROM Member m WHERE 1 = 1"
                + " AND (:userId is null OR m.userId = :userId)"
                + " AND (:password is null OR m.userId = :password)"
                + " AND (:phoneNumber is null OR m.phoneNumber = :phoneNumber)",
                Member.class)
                .setParameter("userId", this.userId)
                .setParameter("password", this.password)
                .setParameter("phoneNumber", this.phoneNumber)
                .getResultList();
        }

        public List<Member> toList() {
            return getResult();
        }

        public Optional<Member> toOptional() {
            List<Member> result = getResult();
            if (result.size() > 1) { throw new NonUniqueResultException(); }
            if (result.size() == 0) { return Optional.empty(); }
            return Optional.of(result.get(0));
        }

        public boolean isEmpty() {
            return this.getResult().isEmpty();
        }
    }

    public FindBy findBy() {
        return new FindBy(this.em);
    }
}
