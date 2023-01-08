package harmony.dev.harmonyserver.security;

import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        List<Member> getMember = memberRepository.findByUserId(userId);

        if (getMember.size() != 1) {
            throw new UsernameNotFoundException("사용자 정보 없음");
        }
        return new UserDetailsImpl(getMember.get(0));
    }
}
