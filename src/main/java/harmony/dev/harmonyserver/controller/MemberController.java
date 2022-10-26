package harmony.dev.harmonyserver.controller;

import harmony.dev.harmonyserver.DTO.MemberFindDTO;
import harmony.dev.harmonyserver.DTO.ResponseDTO;
import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 가입 API
     * Required: user_id, password, phone_number
     * Method Code: 01
     * Error Type
         * 01: Duplicate for user_id
         * 02: Duplicate for phone_number
     */
    @PostMapping("/member/join")
    @ResponseBody
    public ResponseEntity<ResponseDTO> join(@Valid @RequestBody Member member){
        Pair<Boolean, String> resultPair = memberService.join(member);
        ResponseDTO responseDTO = new ResponseDTO();

        if (!resultPair.getFirst()){
            if (resultPair.getSecond().equals("userId")){
                responseDTO.setMessage("이미 사용 중인 아이디입니다.");
                responseDTO.setErrorCode("0101");
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }else if (resultPair.getSecond().equals("phoneNumber")){
                responseDTO.setMessage("이미 사용 중인 휴대폰 번호입니다.");
                responseDTO.setErrorCode("0102");
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }
        }

        responseDTO.setMessage("OK");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * 회원 검색 API
     * Required: type, key
     * Method Code: 02
     * Error Type
        * 01: Type Error
     */
    @PostMapping("/member/find")
    @ResponseBody
    public ResponseEntity<ResponseDTO> find(@Valid @RequestBody MemberFindDTO memberFindDTO){
        String memberFindDTOType = memberFindDTO.getType();
        ResponseDTO responseDTO = new ResponseDTO();

        Optional<Member> result;
        switch(memberFindDTOType) {
            case "user_id":
                result = memberService.findByUserId(memberFindDTO.getKey());
                break;
            case "phone_number":
                result = memberService.findByPhoneNumber(memberFindDTO.getKey());
                break;
            default:
                responseDTO.setMessage("잘못된 타입입니다.");
                responseDTO.setErrorCode("0201");
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }

        responseDTO.setMessage("OK");
        responseDTO.setData(result);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
