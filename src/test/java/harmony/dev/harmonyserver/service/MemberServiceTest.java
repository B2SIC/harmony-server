package harmony.dev.harmonyserver.service;

import harmony.dev.harmonyserver.Exception.BusinessException;
import harmony.dev.harmonyserver.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

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
}
