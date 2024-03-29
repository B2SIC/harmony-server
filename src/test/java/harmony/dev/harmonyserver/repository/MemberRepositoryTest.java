package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("회원 검색 테스트")
    public void findTest(){
        Member member1 = new Member("testid1", "password", "01011111111");
        Member member2 = new Member("testid2", "password", "01011111112");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Optional<Member> uniqueMember;
        Optional<Member> searchResult;

        uniqueMember = memberRepository.findByUserId(member1.getUserId());

        assertThat(uniqueMember.isPresent()).isTrue();
        assertThat(uniqueMember.get()).isEqualTo(member1);

        uniqueMember = memberRepository.findByPhoneNumber(member1.getPhoneNumber());
        assertThat(uniqueMember.isPresent()).isTrue();
        assertThat(uniqueMember.get()).isEqualTo(member1);

        uniqueMember = memberRepository.findByUserId("invalid");
        assertThat(uniqueMember.isPresent()).isFalse();

        searchResult = memberRepository.findByOptionalParameters(member1.getUserId(), null);
        assertThat(searchResult.isPresent()).isTrue();
        assertThat(searchResult.get()).isEqualTo(member1);

        searchResult = memberRepository.findByOptionalParameters(member1.getUserId(), member2.getPhoneNumber());
        assertThat(searchResult.isPresent()).isFalse();
    }
}
