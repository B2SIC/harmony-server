package harmony.dev.harmonyserver.Exception;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {
    private final int status;
    private final int code;
    private final String message;

    private final List<FieldError> errors;

    @Builder
    public ErrorResponse(int status, int code, String message, List<FieldError> errors){
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = initError(errors);
    }

    private List<FieldError> initError(List<FieldError> errors){
        if (errors == null){
            return new ArrayList<>();
        }
        return errors;
    }

    @Getter
    public static class FieldError {
        private final String field;
        private final String value;
        private final String message;

        @Builder
        public FieldError(String field, String value, String message){
            this.field = camelToSnake(field);
            this.value = value;
            this.message = message;
        }

        private static String camelToSnake(String str){
            StringBuilder sb = new StringBuilder();

            sb.append(Character.toLowerCase(str.charAt(0)));

            for (int i = 1; i < str.length(); i++){
                char chr = str.charAt(i);
                if (Character.isUpperCase(chr)){
                    sb.append('_');
                }
                sb.append(Character.toLowerCase(chr));
            }
            return sb.toString();
        }
    }
}
