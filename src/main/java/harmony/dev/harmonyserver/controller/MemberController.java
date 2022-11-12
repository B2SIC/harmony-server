package harmony.dev.harmonyserver.controller;

import harmony.dev.harmonyserver.DTO.ResponseDTO;
import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NonUniqueResultException;
import javax.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // FIXME: Update comment
    /**
     * 회원 검색 API
     * Method Code: 10
     * Required: None (But one must come in)
     * Field: user_id, phone_number
     * Error Type
        * 1001: Duplicate data exists within the server.
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO getMembers(@RequestParam Map<String, String> params) {
        return ResponseDTO.builder()
                          .data(memberService.getMembers(params))
                          .build();
   }

    // FIXME: Update comment
    /**
     * 회원 가입 API
     * Method Code: 11
     * Required: user_id, password, phone_number
     * Error Type
         * 1101: Duplicate for user_id
         * 1102: Duplicate for phone_number
         * 1103: Duplicate data exists within the server.
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO signUpMember(@Valid @RequestBody Member member){
        return ResponseDTO.builder()
                          .data(memberService.signUpMember(member))
                          .build();
    }

    @ExceptionHandler(NonUniqueResultException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseDTO handleLogicalException(NonUniqueResultException e) {
        return ResponseDTO.builder()
                          // TODO: Add .errors()
                          .build();
    }
}
