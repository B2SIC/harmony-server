package harmony.dev.harmonyserver.Exception.Member;

import lombok.Getter;

@Getter
public class PhoneNumberDuplicationException extends RuntimeException{
    private final int errorCode;

    public PhoneNumberDuplicationException(int errorCode) {
        this.errorCode = errorCode;
    }
}
