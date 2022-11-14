package harmony.dev.harmonyserver.service;

import harmony.dev.harmonyserver.Exception.ExceptionSummary;
import harmony.dev.harmonyserver.Exception.BusinessException;
import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional  // FIXME: Move to each transaction method
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getMembers(Map<String, String> params) {
        // FIXME: Use constants
        return memberRepository.findByOptionalParameters(
            params.get("userId"),
            params.get("phoneNumber")
        );
    }

    public Member signUpMember(Member member) {
        validateBeforeSignupMember(member);
        memberRepository.save(member);
        return member;
    }

    private void validateBeforeSignupMember(Member member) {
        // FIXME: Use constants
        BusinessException e = new BusinessException();
        if (memberRepository.findByUserId(member.getUserId()).isPresent()) {
            e.add(ExceptionSummary.builder()
                                  .field("userId")
                                  .value(member.getUserId())
                                  .code("Duplicated")
                                  .build());
        }
        if (memberRepository.findByPhoneNumber(member.getPhoneNumber()).isPresent()) {
            e.add(ExceptionSummary.builder()
                                  .field("phoneNumber")
                                  .value(member.getPhoneNumber())
                                  .code("Duplicated")
                                  .build());
        }
        e.peekaboo();
    }
}
