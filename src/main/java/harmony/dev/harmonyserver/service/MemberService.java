package harmony.dev.harmonyserver.service;

import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Pair<Boolean, String> join(Member member) {
        Pair<Boolean, String> resultPair = validateDuplicateMember(member);

        if (resultPair.getFirst()){
            memberRepository.save(member);
        }
        return resultPair;
    }

    private Pair<Boolean, String> validateDuplicateMember(Member member) {
        if (memberRepository.findByUserId(member.getUserId()).isPresent()){
            return Pair.of(false, "userId");
        }else if(memberRepository.findByPhoneNumber(member.getPhoneNumber()).isPresent()){
            return Pair.of(false, "phoneNumber");
        }else{
            return Pair.of(true, "ok");
        }
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
