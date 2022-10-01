package harmony.dev.harmonyserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@JsonIgnoreProperties(allowSetters = true, value = {"id", "password", "sendMessage"})
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotBlank
    @Size(min = 4, max = 20)
    @JsonProperty("user_id")
    private String userId;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    @NotBlank
    @Size(max = 15)
    @JsonProperty("phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "sender")
    private List<Message> sendMessage = new ArrayList<>();

//    @OneToMany(mappedBy = "receiver")
//    private List<Message> receivedMessage = new ArrayList<>();


    public Member(String userId, String password, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
