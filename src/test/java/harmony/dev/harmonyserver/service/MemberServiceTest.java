package harmony.dev.harmonyserver.service;

import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import javax.transaction.Transactional;
import java.util.List;

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
        /*
        String testId = "testid0";
        Member member = new Member(testId, "password", "01011112222");
        memberService.join(member);

        List<Member> findMember = memberService.findByUserId(testId);
        assertThat(findMember.isEmpty()).isFalse();
        assertThat(findMember.get(0)).isEqualTo(member);
        assertThat(member.getUserId()).isEqualTo(findMember.get(0).getUserId());
        assertThat(member.getPhoneNumber()).isEqualTo(findMember.get(0).getPhoneNumber());
        */
    }

    @Test
    /*
        회원가입 중복 아이디 예외 테스트
     */
    public void joinDuplicateId(){
        /*
        Member member1 = new Member("fortestid", "password", "01099999999");
        Member member2 = new Member("fortestid", "password", "01099999999");  // ID Duplication
        Member member3 = new Member("fortestid2", "password", "01099999999"); // PhoneNumber Duplication
        Member member4 = new Member("fortestid2", "password", "01088888888"); // OK
        memberService.join(member1);

        Pair<Boolean, String> idDuplicationResult = memberService.join(member2);
        assertThat(idDuplicationResult.getFirst()).isFalse();
        assertThat(idDuplicationResult.getSecond()).isEqualTo("userId");

        Pair<Boolean, String> phoneNumberDuplicationResult = memberService.join(member3);
        assertThat(phoneNumberDuplicationResult.getFirst()).isFalse();
        assertThat(phoneNumberDuplicationResult.getSecond()).isEqualTo("phoneNumber");

        Pair<Boolean, String> result = memberService.join(member4);
        assertThat(result.getFirst()).isTrue();

        List<Member> forTestIdUser = memberService.findByUserId(member1.getUserId());
        List<Member> forTestId2User = memberService.findByUserId(member4.getUserId());

        assertThat(forTestIdUser.isEmpty()).isFalse();
        assertThat(forTestId2User.isEmpty()).isFalse();
        assertThat(forTestIdUser.get(0)).isEqualTo(member1);
        assertThat(forTestId2User.get(0)).isEqualTo(member4);
        */
    }

}
