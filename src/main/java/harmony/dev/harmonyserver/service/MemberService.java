package harmony.dev.harmonyserver.service;

import harmony.dev.harmonyserver.Exception.BusinessException;
import harmony.dev.harmonyserver.Exception.ExceptionSummary;
import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.repository.MemberRepository;
import harmony.dev.harmonyserver.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional  // FIXME: Move to each transaction method
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Optional<Member> getMembers(Map<String, String> params) {
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

    public Map<String, String> login(String userId, String password) {
        Optional<Member> findMember = memberRepository.findByUserId(userId);

        if (findMember.isEmpty() || !(findMember.get().getPassword().equals(password))) {
            new BusinessException(
                    ExceptionSummary.builder()
                            .message("Login Fail")
                            .build()
            ).peekaboo();
        }
        return jwtTokenProvider.createToken(findMember.get().getUserId());

    }
}
