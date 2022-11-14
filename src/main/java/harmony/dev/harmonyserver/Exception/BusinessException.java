package harmony.dev.harmonyserver.Exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

// FIXME: Add JavaDoc
@Getter
public class BusinessException extends RuntimeException {
    List<ExceptionSummary> exceptions;

    public BusinessException() {
        this.exceptions = new ArrayList<ExceptionSummary>();
    }

    public BusinessException(ExceptionSummary e) {
        this.exceptions = List.of(e);
    }

    public boolean add(ExceptionSummary e) {
        return this.exceptions.add(e);
    }

    public void peekaboo() {
        if(!this.exceptions.isEmpty()) throw this;
    }
}
