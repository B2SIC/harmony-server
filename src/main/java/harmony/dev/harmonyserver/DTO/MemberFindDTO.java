package harmony.dev.harmonyserver.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFindDTO {
    @NotEmpty
    private String type;
    @NotEmpty
    private String key;
}
