package harmony.dev.harmonyserver.service;

import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        Member member = new Member(testId, "password", "01011112222");
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
        Member member1 = new Member("forTestId", "password", "01099999999");
        Member member2 = new Member("forTestId", "password", "01099999999");  // ID Duplication
        Member member3 = new Member("forTestId2", "password", "01099999999"); // PhoneNumber Duplication
        Member member4 = new Member("forTestId2", "password", "01088888888"); // OK
        memberService.join(member1);

        Pair<Boolean, String> idDuplicationResult = memberService.join(member2);
        assertThat(idDuplicationResult.getFirst()).isFalse();
        assertThat(idDuplicationResult.getSecond()).isEqualTo("userId");

        Pair<Boolean, String> phoneNumberDuplicationResult = memberService.join(member3);
        assertThat(phoneNumberDuplicationResult.getFirst()).isFalse();
        assertThat(phoneNumberDuplicationResult.getSecond()).isEqualTo("phoneNumber");

        Pair<Boolean, String> result = memberService.join(member4);
        assertThat(result.getFirst()).isTrue();

        Optional<Member> forTestIdUser = memberService.findByUserId(member1.getUserId());
        Optional<Member> forTestId2User = memberService.findByUserId(member4.getUserId());

        assertThat(forTestIdUser.isPresent()).isTrue();
        assertThat(forTestId2User.isPresent()).isTrue();
        assertThat(forTestIdUser.get()).isEqualTo(member1);
        assertThat(forTestId2User.get()).isEqualTo(member4);
    }

}
