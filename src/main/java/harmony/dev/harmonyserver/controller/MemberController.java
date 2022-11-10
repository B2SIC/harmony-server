package harmony.dev.harmonyserver.controller;

import harmony.dev.harmonyserver.DTO.ResponseDTO;
import harmony.dev.harmonyserver.Exception.ErrorCode;
import harmony.dev.harmonyserver.Exception.ErrorResponse;
import harmony.dev.harmonyserver.Exception.Member.NotUniqueDataException;
import harmony.dev.harmonyserver.Exception.Member.PhoneNumberDuplicationException;
import harmony.dev.harmonyserver.Exception.Member.UserIdDuplicationException;
import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 검색 API
     * Method Code: 10
     * Required: None (But one must come in)
     * Field: user_id, phone_number
     * Error Type
        * 1001: Duplicate data exists within the server.
     */
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<ResponseDTO> find(@RequestParam(value = "user_id", required = false) String userId,
                                            @RequestParam(value = "phone_number", required = false) String phoneNumber
                                            ) throws MissingServletRequestParameterException {
        ResponseDTO responseDTO = new ResponseDTO();
        List<Member> result;

        if (userId != null){
            try{
                result = memberService.findByUserId(userId);
            } catch (NotUniqueDataException ex){
                throw new NotUniqueDataException(1001, ex.getMessage());
            }
        } else if (phoneNumber != null) {
            try{
                result = memberService.findByPhoneNumber(phoneNumber);
            } catch (NotUniqueDataException ex){
                throw new NotUniqueDataException(1001, ex.getMessage());
            }
        } else{
            throw new MissingServletRequestParameterException("user_id or phone_number", "String");
        }

        responseDTO.setMessage("OK");
        responseDTO.setStatusCode(200);
        responseDTO.setData(result);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

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
    @ResponseBody
    public ResponseEntity<ResponseDTO> join(@Valid @RequestBody Member member){
        try{
            Pair<Boolean, String> resultPair = memberService.join(member);
            ResponseDTO responseDTO = new ResponseDTO();

            if (!resultPair.getFirst()){
                if (resultPair.getSecond().equals("userId")){
                    throw new UserIdDuplicationException(1101);
                }else if (resultPair.getSecond().equals("phoneNumber")){
                    throw new PhoneNumberDuplicationException(1102);
                }
            }
            responseDTO.setStatusCode(200);
            responseDTO.setMessage("OK");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (NotUniqueDataException ex){
            throw new NotUniqueDataException(1103, ex.getMessage());
        }
    }

    @ExceptionHandler(UserIdDuplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse userIdDuplicatedException(UserIdDuplicationException ex){
        ErrorCode errorCode = ErrorCode.ID_DUPLICATED;
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(ex.getErrorCode())
                .message(errorCode.getMessage())
                .build();
    }

    @ExceptionHandler(PhoneNumberDuplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse phoneNumberDuplicatedException(PhoneNumberDuplicationException ex){
        ErrorCode errorCode = ErrorCode.PHONE_NUMBER_DUPLICATED;
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(ex.getErrorCode())
                .message(errorCode.getMessage())
                .build();
    }

    @ExceptionHandler(NotUniqueDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse notUniqueDataException(NotUniqueDataException ex){
        ErrorCode errorCode = ErrorCode.NOT_UNIQUE_DATA;
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .build();
    }
}
