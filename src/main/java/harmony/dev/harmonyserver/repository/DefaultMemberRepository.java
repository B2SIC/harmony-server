package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class DefaultMemberRepository implements MemberRepository{

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
    public Optional<Member> findByUserId(String userId) {
        List<Member> result = em.createQuery("SELECT m from Member m WHERE m.userId = :userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();

        if(result.size() <= 1){
            return result.stream().findAny();
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "중복된 아이디가 존재합니다.");
        }
    }

    @Override
    public Optional<Member> findByPhoneNumber(String phoneNumber) {
        List<Member> result = em.createQuery("SELECT m from Member m WHERE m.phoneNumber = :phoneNumber", Member.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();

        if(result.size() <= 1){
            return result.stream().findAny();
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "중복된 휴대폰 번호가 존재합니다.");
        }
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("SELECT m from Member m", Member.class)
                .getResultList();
    }
}
