package harmony.dev.harmonyserver.service;

import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.repository.MemberRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return "OK";
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByUserId(member.getUserId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 사용 중인 아이디입니다.");
                });

        memberRepository.findByPhoneNumber(member.getPhoneNumber())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 사용 중인 휴대폰 번호입니다.");
                });
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findByUserId(String userId){
        return memberRepository.findByUserId(userId);
    }

    public Optional<Member> findByPhoneNumber(String phoneNumber){
        return memberRepository.findByPhoneNumber(phoneNumber);
    }
}
