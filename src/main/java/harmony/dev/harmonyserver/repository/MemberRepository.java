package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.domain.Member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByPhoneNumber(String phoneNumber);

    @Query("SELECT m FROM Member m WHERE 1 = 1"
         + " AND (:userId is null OR m.userId = :userId)"
         + " AND (:phoneNumber is null OR m.phoneNumber = :phoneNumber)")
     // FIXME: Use constants
    List<Member> findByOptionalParameters(@Param("userId") String userId,
                                          @Param("phoneNumber") String phoneNumber);
}
