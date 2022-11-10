package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.domain.Member;

import java.util.List;

public interface MemberRepository {
    Member save(Member member);
    List<Member> findByUserId(String userId);
    List<Member> findByPhoneNumber(String phoneNumber);
    List<Member> findAll();
}
