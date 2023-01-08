package harmony.dev.harmonyserver.service;

import harmony.dev.harmonyserver.Exception.BusinessException;
import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired private MemberService memberService;
    @Autowired private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("회원가입 테스트")
    public void signUpTest() {
        Member member1 = new Member("testid1", "password", "01011112222");
        Member member2 = new Member("testid1", "password", "01022221111");
        Member member3 = new Member("testid2", "password", "01011112222");
        Member member4 = new Member("testid2", "password", "01022221111");

        assertThat(member1).isEqualTo(memberService.signUpMember(member1));

        BusinessException businessException;
        businessException = assertThrows(BusinessException.class, () -> {
            memberService.signUpMember(member2);
        });
        assertThat(businessException.getExceptions().size()).isEqualTo(1);
        assertThat(businessException.getExceptions().get(0).getField()).isEqualTo("userId");
        assertThat(businessException.getExceptions().get(0).getCode()).isEqualTo("Duplicated");

        businessException = assertThrows(BusinessException.class, () -> {
            memberService.signUpMember(member3);
        });
        assertThat(businessException.getExceptions().size()).isEqualTo(1);
        assertThat(businessException.getExceptions().get(0).getField()).isEqualTo("phoneNumber");
        assertThat(businessException.getExceptions().get(0).getCode()).isEqualTo("Duplicated");

        businessException = assertThrows(BusinessException.class, () -> {
            memberService.signUpMember(member1);
        });
        assertThat(businessException.getExceptions().size()).isEqualTo(2);
        assertThat(memberService.signUpMember(member4)).isEqualTo(member4);
    }

    @Test
    @DisplayName("로그인 및 토큰 발급 테스트")
    public void loginTest() {
        Member newMember = new Member("testid", "testpw", "01012345678");
        memberService.signUpMember(newMember);

        assertThrows(BusinessException.class, () ->
                memberService.login("testid", "wrongpw") // 1. 틀린 비밀번호
        );
        assertThrows(BusinessException.class, () ->
                memberService.login("wrongid", "testpw") // 2. 존재하지 않는 아이디
        );
        assertThrows(BusinessException.class, () ->
                memberService.login("wrongid", "wrongpw") // 1 + 2
        );

        Map<String, String> getLoginResult = memberService.login("testid", "testpw");
        String token = getLoginResult.get("key");
        assertThat(jwtTokenProvider.checkValidationToken(token)).isTrue();
        assertThat(jwtTokenProvider.getUserIdFromToken(token)).isEqualTo(newMember.getUserId());
    }
}
