package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void findTest(){
        /*
        Member member1 = new Member("testid1", "password", "01011111111");
        memberRepository.save(member1);

        Member member2 = new Member("testid2", "password", "01022222222");
        memberRepository.save(member2);

        List<Member> userIdTest = memberRepository.findByUserId("testid2");
        assertThat(userIdTest.isEmpty()).isFalse();
        assertThat(userIdTest.get(0)).isEqualTo(member2);

        List<Member> phoneNumberTest = memberRepository.findByPhoneNumber("01011111111");
        assertThat(phoneNumberTest.isEmpty()).isFalse();
        assertThat(phoneNumberTest.get(0)).isEqualTo(member1);

        // 결과가 존재하지 않음
        userIdTest = memberRepository.findByUserId("testid3");
        assertThat(userIdTest.isEmpty()).isTrue();
        */
    }

    @Test
    public void findAllTest(){
        /*
        List<Member> alreadyExists = memberRepository.findAll();
        int dataCount = alreadyExists.size();

        Member member1 = new Member("testid1", "password", "01011111111");
        memberRepository.save(member1);

        Member member2 = new Member("testid2", "password", "01022222222");
        memberRepository.save(member2);

        Member member3 = new Member("testid3", "password", "01033333333");
        memberRepository.save(member3);

        List<Member> result = memberRepository.findAll();
        assertThat(result.size()).isEqualTo(dataCount + 3);
        */
    }
}
