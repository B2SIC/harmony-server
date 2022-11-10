package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.Exception.Member.NotUniqueDataException;
import harmony.dev.harmonyserver.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    private List<Member> findUniqueByValue(String column, String value) throws NotUniqueDataException{
        List<Member> findResult = findByValue(column, value);

        if (findResult.size() > 1){
            String message = "알 수 없는 데이터 오류가 발생했습니다.";
            switch (column){
                case "userId":
                    message = "중복된 아이디가 존재합니다.";
                    break;
                case "phoneNumber":
                    message = "중복된 휴대폰 번호가 존재합니다.";
                    break;
            }
            throw new NotUniqueDataException(message);
        }

        return findResult;
    }

    @Override
    public List<Member> findByUserId(String userId) {
        return findUniqueByValue("userId", userId);
    }

    @Override
    public List<Member> findByPhoneNumber(String phoneNumber) {
        return findUniqueByValue("phoneNumber", phoneNumber);
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("SELECT m from Member m", Member.class)
                .getResultList();
    }
}
