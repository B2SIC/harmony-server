package harmony.dev.harmonyserver.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    @NotNull @Pattern(regexp = ".+")  // FIXME: Check that the input is a hash
    private String password;

    @NotNull @Pattern(regexp = "^010\\d{8}")
    private String phoneNumber;


    public Member(String userId, String password, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
