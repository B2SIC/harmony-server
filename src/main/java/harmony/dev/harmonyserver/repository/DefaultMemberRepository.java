package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DefaultMemberRepository implements MemberRepository{

    private final EntityManager em;

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    private List<Member> findByValue(String column, String value){
        return em.createQuery("SELECT m from Member m WHERE m." + column + " = :value", Member.class)
                .setParameter("value", value)
                .getResultList();
    }

    private Optional<Member> findUniqueByValue(String column, String value){
        List<Member> findResult = findByValue(column, value);

        if (findResult.size() > 1){
            String message;
            switch (column){
                case "userId":
                    message = "중복된 아이디가 존재합니다.";
                    break;
                case "phoneNumber":
                    message = "중복된 휴대폰 번호가 존재합니다.";
                    break;
                default:
                    message = "알 수 없는 오류가 발생했습니다.";
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message);
        }

        return findResult.stream().findAny();
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        return findUniqueByValue("userId", userId);
    }

    @Override
    public Optional<Member> findByPhoneNumber(String phoneNumber) {
        return findUniqueByValue("phoneNumber", phoneNumber);
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("SELECT m from Member m", Member.class)
                .getResultList();
    }
}
