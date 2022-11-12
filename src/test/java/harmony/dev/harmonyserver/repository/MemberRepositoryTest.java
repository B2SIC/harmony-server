package harmony.dev.harmonyserver.repository;

import harmony.dev.harmonyserver.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void findTest(){
        Member member1 = new Member("testid1", "password", "01011111111");
        Member member2 = new Member("testid2", "password", "01011111112");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Optional<Member> uniqueMember;
        List<Member> searchResult;

        uniqueMember = memberRepository.findByUserId(member1.getUserId());
        assertTrue(uniqueMember.isPresent());
        assertEquals(member1, uniqueMember.get());

        uniqueMember = memberRepository.findByPhoneNumber(member1.getPhoneNumber());
        assertTrue(uniqueMember.isPresent());
        assertEquals(member1, uniqueMember.get());

        uniqueMember = memberRepository.findByUserId("invalid");
        assertFalse(uniqueMember.isPresent());

        searchResult = memberRepository.findByOptionalParameters(null, null);
        assertEquals(2, searchResult.size());

        searchResult = memberRepository.findByOptionalParameters(member1.getUserId(), null);
        assertEquals(1, searchResult.size());
        assertEquals(member1, searchResult.get(0));

        searchResult = memberRepository.findByOptionalParameters(member1.getUserId(), member2.getPhoneNumber());
        assertEquals(0, searchResult.size());
    }
}
