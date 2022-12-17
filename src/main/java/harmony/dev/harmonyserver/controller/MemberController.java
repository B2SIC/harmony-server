package harmony.dev.harmonyserver.controller;

import harmony.dev.harmonyserver.DTO.ResponseDTO;
import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Map;

/**
 * Member 관련 요청을 처리
 * @see harmony.dev.harmonyserver.Exception.ExceptionAdvisor
 * @throws MethodArgumentNotValidException    @Valid 조건에 맞지 않음
 * @throws HttpMessageNotReadableException    json 파싱 중 에러 발생
 * @throws HttpMediaTypeNotSupportedException body type이 json이 아님
 */
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * @param userId
     * @param phoneNumber
     * @return 주어진 param과 일치하는 member 목록 반환
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO getMembers(@RequestParam Map<String, String> params) {
        return ResponseDTO.builder()
                          .data(Member.ResponseDto.from(memberService.getMembers(params)))
                          .build();
   }

    /**
     * @return 가입에 성공한 경우 member 정보 반환
     * @throws BusinessException 같은 정보로 가입한 회원이 이미 존재
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO signUpMember(@Valid @RequestBody Member member){
        return ResponseDTO.builder()
                          .data(Member.ResponseDto.from(memberService.signUpMember(member)))
                          .build();
    }
}
