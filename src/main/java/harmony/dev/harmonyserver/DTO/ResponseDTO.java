package harmony.dev.harmonyserver.DTO;

import java.util.ArrayList;
import java.util.List;

import harmony.dev.harmonyserver.Exception.ExceptionSummary;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDTO {
    private Object data;

    @Builder.Default
    private final List<ExceptionSummary> errors = new ArrayList<ExceptionSummary>();
}
