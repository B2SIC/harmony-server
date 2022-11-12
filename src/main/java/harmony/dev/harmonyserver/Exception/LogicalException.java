package harmony.dev.harmonyserver.Exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

// FIXME: Add JavaDoc
@Getter
public class LogicalException extends RuntimeException {
    List<ExceptionSummary> exceptions;

    public LogicalException() {
        this.exceptions = new ArrayList<ExceptionSummary>();
    }

    public LogicalException(ExceptionSummary e) {
        this.exceptions = List.of(e);
    }

    public boolean AddExceptionSummary(ExceptionSummary e) {
        return this.exceptions.add(e);
    }

    public boolean isEmpty() {
        return this.exceptions.isEmpty();
    }
}
