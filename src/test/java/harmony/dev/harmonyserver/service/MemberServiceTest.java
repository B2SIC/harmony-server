package harmony.dev.harmonyserver.service;

import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
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
        member.setPhoneNumber("01012345678");
        memberService.join(member);

        Member findMember = memberService.findByUserId(testId).get();
        assertThat(member.getUserId()).isEqualTo(findMember.getUserId());
    }

    @Test
    /*
        회원가입 중복 아이디 예외 테스트
     */
    public void joinDuplicateId(){
        Member member1 = new Member();
        member1.setUserId("forTestId");
        member1.setPassword("password");
        member1.setPhoneNumber("01012345678");

        Member member2 = new Member();
        member2.setUserId("forTestId");
        member2.setPassword("password");
        member2.setPhoneNumber("01012345678");

        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 사용 중인 아이디입니다.");
    }

}
