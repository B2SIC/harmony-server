package harmony.dev.harmonyserver.DTO;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class MemberFindDTO {
    @NotEmpty
    private String type;
    @NotEmpty
    private String key;
}
