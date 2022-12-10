package harmony.dev.harmonyserver.Exception;


import harmony.dev.harmonyserver.DTO.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionAdvisor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseDTO.builder()
                          .errors(ExceptionSummary.of(ex.getBindingResult()))
                          .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseDTO.builder()
                          .errors(List.of(ExceptionSummary.builder()
                                                          .code("UnreadableMessage")
                                                          .build()))
                          .build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseDTO handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        ArrayList<ExceptionSummary> errors = new ArrayList<>();
        errors.add(ExceptionSummary.builder()
                                   .code("UnsupportedType")
                                   .build());
        return ResponseDTO.builder()
                          .errors(errors)
                          .build();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseDTO handleBusinessException(BusinessException ex) {
        return ResponseDTO.builder()
                          .errors(ExceptionSummary.of(ex))
                          .build();
    }
}
