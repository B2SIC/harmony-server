package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByOptionalParameters(String userId, String phoneNumber);
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByPhoneNumber(String phoneNumber);
}
