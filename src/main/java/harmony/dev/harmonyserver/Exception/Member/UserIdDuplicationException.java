package harmony.dev.harmonyserver.Exception.Member;

import lombok.Getter;

@Getter
public class UserIdDuplicationException extends RuntimeException{
    private final int errorCode;

    public UserIdDuplicationException(int errorCode) {
        this.errorCode = errorCode;
    }
}
