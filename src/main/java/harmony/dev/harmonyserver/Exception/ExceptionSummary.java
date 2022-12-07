package harmony.dev.harmonyserver.Exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import lombok.Builder;
import lombok.Getter;

// FIXME: Add JavaDoc
@Getter
@Builder
public class ExceptionSummary {

    private final String field;
    private final String code;
    private final String value;
    private final String message;

    public static List<ExceptionSummary> of(BindingResult bindingResult) {
        if(!bindingResult.hasFieldErrors()) return null;

        return bindingResult.getFieldErrors().parallelStream()
            .map(e -> ExceptionSummary.builder()
                                      .field(e.getField())
                                      .code(e.getCode())
                                      .value((String)e.getRejectedValue())
                                      .message(e.getDefaultMessage())
                                      .build())
            .collect(Collectors.toList());
    }

    public static List<ExceptionSummary> of(BusinessException businessException) {
        return businessException.getExceptions();
    }
}
