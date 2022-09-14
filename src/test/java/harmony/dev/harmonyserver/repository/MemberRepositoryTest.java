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
        Member member1 = new Member();
        member1.setUserId("testId1");
        member1.setPassword("passwd");
        member1.setPhoneNumber("01011111111");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setUserId("testId2");
        member2.setPassword("passwd");
        member2.setPhoneNumber("01022222222");
        memberRepository.save(member2);

        Member userIdTest = memberRepository.findByUserId("testId2").get();
        assertThat(userIdTest).isEqualTo(member2);
        assertThat(userIdTest).isNotEqualTo(member1);

        Member phoneNumberTest = memberRepository.findByPhoneNumber("01011111111").get();
        assertThat(phoneNumberTest).isEqualTo(member1);
        assertThat(phoneNumberTest).isNotEqualTo(member2);
    }

    @Test
    public void findAllTest(){
        List<Member> alreadyExists = memberRepository.findAll();
        int dataCount = alreadyExists.size();

        Member member1 = new Member();
        member1.setUserId("testId1");
        member1.setPassword("passwd");
        member1.setPhoneNumber("01011111111");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setUserId("testId2");
        member2.setPassword("passwd");
        member2.setPhoneNumber("01022222222");
        memberRepository.save(member2);

        Member member3 = new Member();
        member3.setUserId("testId3");
        member3.setPassword("passwd");
        member3.setPhoneNumber("01033333333");
        memberRepository.save(member3);

        List<Member> result = memberRepository.findAll();
        assertThat(result.size()).isEqualTo(dataCount + 3);
    }
}
