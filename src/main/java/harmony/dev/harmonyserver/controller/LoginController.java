package harmony.dev.harmonyserver.controller;

import harmony.dev.harmonyserver.DTO.ResponseDTO;
import harmony.dev.harmonyserver.domain.Member;
import harmony.dev.harmonyserver.form.LoginForm;
import harmony.dev.harmonyserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO login(@Valid @RequestBody LoginForm form,
                             HttpServletResponse response, HttpServletRequest request) {
        Member loginMember = memberService.login(form.getUserId(), form.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);
        return ResponseDTO.builder()
                .data("Login Success")
                .build();
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO logout(HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseDTO.builder()
                .data(null)
                .build();
    }
}
