package harmony.dev.harmonyserver.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter
public class Message {
    @Id @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member sender;

    @NotEmpty
    private String receiver;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String title;

    @NotEmpty
    @Size(min = 1, max = 1000)
    private String content;
    private Date time;

}
