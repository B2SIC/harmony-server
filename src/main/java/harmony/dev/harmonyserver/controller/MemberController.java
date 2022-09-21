package harmony.dev.harmonyserver.controller;

import harmony.dev.harmonyserver.DTO.MemberFindDTO;
import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member/join")
    @ResponseBody
    public Map<String, String> join(@Valid @RequestBody Member member){
        String result = memberService.join(member);
        Map<String, String> resultMap = new LinkedHashMap<>();

        if(result.equals("OK")){
            resultMap.put("message", "OK");
        }else{
            resultMap.put("message", "Fail");
        }
        return resultMap;
    }

    @PostMapping("/member/find")
    @ResponseBody
    public Map<String, Object> find(@Valid @RequestBody MemberFindDTO memberFindDTO){
        String memberFindDTOType = memberFindDTO.getType();
        Map<String, Object> resultMap = new LinkedHashMap<>();

        Optional<Member> result;
        switch(memberFindDTOType) {
            case "user_id":
                result = memberService.findByUserId(memberFindDTO.getKey());
                break;
            case "phone_number":
                result = memberService.findByPhoneNumber(memberFindDTO.getKey());
                break;
            default:
                throw new IllegalStateException("잘못된 타입입니다.");
        }

        resultMap.put("message", "Success");
        resultMap.put("result", result);
        return resultMap;
    }
}
