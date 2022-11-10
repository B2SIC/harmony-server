package harmony.dev.harmonyserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@RequiredArgsConstructor
@JsonIgnoreProperties(allowSetters = true, value = {"id", "password"})
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotBlank
    @Pattern(regexp = "[a-z][a-z0-9]+",
            message = "아이디는 숫자로 시작할 수 없으며 영어 소문자와 숫자로만 구성이 가능합니다.")
    @Size(min = 4, max = 16)
    @JsonProperty("user_id")
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    @Pattern(regexp = "^010\\d{8}",
            message = "올바른 휴대폰 번호 형식이 아닙니다.")
    @JsonProperty("phone_number")
    private String phoneNumber;


    public Member(String userId, String password, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
