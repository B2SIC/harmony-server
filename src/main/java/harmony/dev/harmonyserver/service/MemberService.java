package harmony.dev.harmonyserver.service;

import harmony.dev.harmonyserver.Exception.BusinessException;
import harmony.dev.harmonyserver.Exception.ExceptionSummary;
import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.repository.MemberRepository;
import harmony.dev.harmonyserver.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional  // FIXME: Move to each transaction method
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public List<Member> getMembers(Map<String, String> params) {
        String userId = params.get("userId");
        String phoneNumber = params.get("phoneNumber");

        if (userId == null && phoneNumber == null) {
            BusinessException e = new BusinessException();
            e.add(ExceptionSummary.builder()
                            .code("NotFound")
                            .message("No parameter given")
                            .build());
            e.peekaboo();
        }
        return memberRepository.findByOptionalParameters(userId, phoneNumber);
    }

    public Member signUpMember(Member member) {
        validateBeforeSignupMember(member);
        memberRepository.save(member);
        return member;
    }

    private void validateBeforeSignupMember(Member member) {
        // FIXME: Use constants
        BusinessException e = new BusinessException();
        if (!memberRepository.findByUserId(member.getUserId()).isEmpty()) {
            e.add(ExceptionSummary.builder()
                                  .field("userId")
                                  .value(member.getUserId())
                                  .code("Duplicated")
                                  .build());
        }
        if (!memberRepository.findByPhoneNumber(member.getPhoneNumber()).isEmpty()) {
            e.add(ExceptionSummary.builder()
                                  .field("phoneNumber")
                                  .value(member.getPhoneNumber())
                                  .code("Duplicated")
                                  .build());
        }
        e.peekaboo();
    }

    public Map<String, String> login(String userId, String password) {
        List<Member> userList = memberRepository.findByUserId(userId);

        if (userList.isEmpty()) {
            new BusinessException(
                    ExceptionSummary.builder()
                            .message("Login Fail")
                            .build()
            ).peekaboo();
        }
        Member findUser = userList.get(0);
        if (!findUser.getPassword().equals(password)) {
            new BusinessException(
                    ExceptionSummary.builder()
                            .message("Login Fail")
                            .build()
            ).peekaboo();
        }
        return jwtTokenProvider.createToken(findUser.getUserId());
    }
}
