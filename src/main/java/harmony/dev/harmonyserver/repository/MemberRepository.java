package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    List<Member> findByOptionalParameters(String userId, String phoneNumber);
    List<Member> findByUserId(String userId);
    List<Member> findByPhoneNumber(String phoneNumber);

    interface FindBy {
        List<Member> toList();
        Optional<Member> toOptional();
        boolean isEmpty();

        // Setter with method chaining
        FindBy userId(String userId);
        FindBy password(String password);
        FindBy phoneNumber(String phoneNumber);
    }
    FindBy findBy();
}
