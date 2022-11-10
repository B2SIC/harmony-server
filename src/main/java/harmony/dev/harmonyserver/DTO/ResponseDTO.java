package harmony.dev.harmonyserver.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int statusCode;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
}
