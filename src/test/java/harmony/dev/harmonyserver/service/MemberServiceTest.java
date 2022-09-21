package harmony.dev.harmonyserver.service;

import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    /*
        회원가입 테스트
     */
    public void join() {
        String testId = "test_id0";
        Member member = new Member();
        member.setUserId(testId);
        member.setPassword("passwd");
        member.setPhoneNumber("01011112222");
        memberService.join(member);

        Optional<Member> findMember = memberService.findByUserId(testId);
        assertThat(findMember.isPresent()).isTrue();
        assertThat(findMember.get()).isEqualTo(member);
        assertThat(member.getUserId()).isEqualTo(findMember.get().getUserId());
        assertThat(member.getPhoneNumber()).isEqualTo(findMember.get().getPhoneNumber());
    }

    @Test
    /*
        회원가입 중복 아이디 예외 테스트
     */
    public void joinDuplicateId(){
        Member member1 = new Member();
        member1.setUserId("forTestId");
        member1.setPassword("password");
        member1.setPhoneNumber("01099999999");

        Member member2 = new Member();
        member2.setUserId("forTestId");
        member2.setPassword("password");
        member2.setPhoneNumber("01099999999");

        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 사용 중인 아이디입니다.");
        member2.setUserId("forTestId2");

        e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 사용 중인 휴대폰 번호입니다.");
        member2.setPhoneNumber("01088888888");

        String result = memberService.join(member2);
        assertThat(result).isEqualTo("OK");

        Optional<Member> userIdTest = memberService.findByUserId(member1.getUserId());
        Optional<Member> userId2Test = memberService.findByUserId(member2.getUserId());

        assertThat(userIdTest.isPresent()).isTrue();
        assertThat(userId2Test.isPresent()).isTrue();
        assertThat(userIdTest.get()).isEqualTo(member1);
        assertThat(userId2Test.get()).isEqualTo(member2);
    }

}
