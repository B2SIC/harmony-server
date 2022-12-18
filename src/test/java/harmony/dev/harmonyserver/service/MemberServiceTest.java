package harmony.dev.harmonyserver.service;

import harmony.dev.harmonyserver.Exception.BusinessException;
import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.form.LoginForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;

    @Test
    /*
        회원가입 테스트
     */
    public void signUpMember() {
        Member member1 = new Member("testid1", "password", "01011112222");
        Member member2 = new Member("testid1", "password", "01022221111");
        Member member3 = new Member("testid2", "password", "01011112222");
        Member member4 = new Member("testid2", "password", "01022221111");

        BusinessException businessException;

        assertEquals(member1, memberService.signUpMember(member1));

        businessException = assertThrows(BusinessException.class, () -> {
            memberService.signUpMember(member2);
        });
        assertEquals(1, businessException.getExceptions().size());
        assertEquals("userId", businessException.getExceptions().get(0).getField());
        assertEquals("Duplicated", businessException.getExceptions().get(0).getCode());

        businessException = assertThrows(BusinessException.class, () -> {
            memberService.signUpMember(member3);
        });
        assertEquals(1, businessException.getExceptions().size());
        assertEquals("phoneNumber", businessException.getExceptions().get(0).getField());
        assertEquals("Duplicated", businessException.getExceptions().get(0).getCode());

        businessException = assertThrows(BusinessException.class, () -> {
            memberService.signUpMember(member1);
        });
        assertEquals(2, businessException.getExceptions().size());

        assertEquals(member4, memberService.signUpMember(member4));
    }

    @Test
    /*
        로그인 테스트
     */
    public void loginMember() {
        Member member1 = new Member("testid1", "password", "01011112222");
        memberService.signUpMember(member1);

        LoginForm loginSuccess = new LoginForm("testid1", "password");
        LoginForm failPassword = new LoginForm("testid1", "abcdef");
        LoginForm userIdNotExist = new LoginForm("testid2", "password");

        Member loginMember = memberService.login(loginSuccess.getUserId(), loginSuccess.getPassword()); // 성공
        assertEquals(loginMember, member1);

        assertThrows(BusinessException.class,
                () -> memberService.login(failPassword.getUserId(), failPassword.getPassword())); // 틀린 비밀번호
        assertThrows(BusinessException.class,
                () -> memberService.login(userIdNotExist.getUserId(), userIdNotExist.getPassword())); // 없는 유저 아이디
    }
}
