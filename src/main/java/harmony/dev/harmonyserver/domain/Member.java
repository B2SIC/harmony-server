package harmony.dev.harmonyserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@RequiredArgsConstructor
public class Member {
    @Id @GeneratedValue
    private Long id;

    @NotNull @Pattern(regexp = "[a-z][a-z0-9]{3,15}")
    private String userId;

    @NotNull @Pattern(regexp = "[a-fA-F0-9]{64}")
    private String password;

    @NotNull @Pattern(regexp = "^010\\d{8}")
    private String phoneNumber;


    public Member(String userId, String password, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    @Getter
    @AllArgsConstructor
    public static class ResponseDto {
        private String userId;
        private String phoneNumber;

        public static ResponseDto from(Member member) {
            return new ResponseDto(member.userId, member.phoneNumber);
        }

        public static List<ResponseDto> from(List<Member> member) {
            return member.stream()
                         .map(m -> from(m))
                         .collect(Collectors.toList());
        }
    }
}
