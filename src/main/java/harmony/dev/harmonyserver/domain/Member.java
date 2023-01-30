package harmony.dev.harmonyserver.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String userId;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String userId, String password, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = Role.USER;
    }
}
