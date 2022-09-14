package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByPhoneNumber(String phoneNumber);
    List<Member> findAll();
}
