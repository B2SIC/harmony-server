package harmony.dev.harmonyserver.Exception.Member;

import lombok.Getter;

@Getter
public class NotUniqueDataException extends RuntimeException {
    private int errorCode;
    private final String message;

    public NotUniqueDataException(String message) {
        this.message = message;
    }

    public NotUniqueDataException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
