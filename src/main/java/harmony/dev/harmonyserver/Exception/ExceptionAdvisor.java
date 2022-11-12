package harmony.dev.harmonyserver.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import harmony.dev.harmonyserver.DTO.ResponseDTO;

@RestControllerAdvice
public class ExceptionAdvisor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseDTO.builder()
                          .errors(ExceptionSummary.byBindingResult(ex.getBindingResult()))
                          .build();
    }

    @ExceptionHandler(LogicalException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseDTO handleLogicalException(LogicalException e) {
        return ResponseDTO.builder()
                          .errors(e.exceptions)
                          .build();
    }
}
