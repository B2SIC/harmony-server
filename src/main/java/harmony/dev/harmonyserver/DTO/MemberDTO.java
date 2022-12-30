package harmony.dev.harmonyserver.DTO;

import harmony.dev.harmonyserver.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

public class MemberDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotNull @Pattern(regexp = "[a-z][a-z0-9]{3,15}")
        private String userId;
        @NotNull @Pattern(regexp = "[a-fA-F0-9]{64}")
        private String password;
        @NotNull @Pattern(regexp = "^010\\d{8}")
        private String phoneNumber;

        public Member toEntity(){
            return Member.builder()
                    .userId(userId)
                    .password(password)
                    .phoneNumber(phoneNumber)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private String userId;
        private String phoneNumber;

        public static Response from(Member member) {
            return new Response(member.getUserId(), member.getPhoneNumber());
        }

        public static List<Response> from(List<Member> member) {
            return member.stream()
                    .map(Response::from)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String userId;
        private String password;
    }

}
