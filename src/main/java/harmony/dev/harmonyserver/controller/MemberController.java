package harmony.dev.harmonyserver.controller;

import harmony.dev.harmonyserver.DTO.MemberFindDTO;
import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member/join")
    @ResponseBody
    public String join(@Valid @RequestBody Member member){
        return memberService.join(member);
    }

    @PostMapping("/member/find")
    @ResponseBody
    public Optional<Member> find(@Valid @RequestBody MemberFindDTO memberFindDTO){
        if(memberFindDTO.getType().equals("user_id")){
            return memberService.findByUserId(memberFindDTO.getKey());
        }else if(memberFindDTO.getType().equals("phone_number")){
            return memberService.findByPhoneNumber(memberFindDTO.getKey());
        }else{
            throw new IllegalStateException("잘못된 타입입니다.");
        }
    }

}
