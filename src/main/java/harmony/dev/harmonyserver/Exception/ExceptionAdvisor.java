package harmony.dev.harmonyserver.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class ExceptionAdvisor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse validationException(MethodArgumentNotValidException ex){
        List<ErrorResponse.FieldError> errors = extractErrorField(ex.getBindingResult());
        return buildFieldErrors(ErrorCode.VALIDATION_FAIL, errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse notReadableException(HttpMessageNotReadableException ex){
        return buildErrors(ErrorCode.BAD_REQUEST_TYPE);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse parameterNotFoundException(MissingServletRequestParameterException ex){
        List<ErrorResponse.FieldError> errors = makeErrorField(ex.getParameterName(), null, ex.getMessage());
        return buildFieldErrors(ErrorCode.BAD_REQUEST_TYPE, errors);
    }

    /**
     * From BindingResult, extract error details.
     */
    private List<ErrorResponse.FieldError> extractErrorField(BindingResult bindingResult){
        List<FieldError> errors = bindingResult.getFieldErrors();
        return errors.parallelStream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .field(error.getField())
                        .value((String)error.getRejectedValue())
                        .message((error.getDefaultMessage()))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Add error details directly.
     */
    private List<ErrorResponse.FieldError> makeErrorField(String field, String value, String message){
        List<ErrorResponse.FieldError> errors = new ArrayList<>();
        ErrorResponse.FieldError error = ErrorResponse.FieldError.builder()
                .field(field)
                .value(value)
                .message(message)
                .build();
        errors.add(error);
        return errors;
    }

    /**
     * Configure Error Response with Common Error Code
     */
    private ErrorResponse buildErrors(ErrorCode errorCode){
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getErrorCode())
                .message(errorCode.getMessage())
                .build();
    }

    /**
     * Configure Error Response with Custom Error Code
     */
    private ErrorResponse buildCustomErrors(ErrorCode errorCode, int code){
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(code)
                .message(errorCode.getMessage())
                .build();
    }

    /**
     * Configure Error Response with FieldError
     */
    private ErrorResponse buildFieldErrors(ErrorCode errorCode, List<ErrorResponse.FieldError> errors){
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getErrorCode())
                .message(errorCode.getMessage())
                .errors(errors)
                .build();
    }
}
