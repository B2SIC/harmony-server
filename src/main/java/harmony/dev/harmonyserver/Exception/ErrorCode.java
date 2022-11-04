package harmony.dev.harmonyserver.Exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    /**
     * Common Error
     */
    VALIDATION_FAIL(400, 0, "잘못된 요청입니다."),
    BAD_REQUEST_TYPE(400, 1, "잘못된 요청 데이터 형식입니다."),

    /**
     * Method Related Error
     */
    ID_DUPLICATED(400, "이미 사용 중인 아이디입니다."),
    PHONE_NUMBER_DUPLICATED(400,"이미 사용 중인 휴대폰 번호입니다."),
    NOT_UNIQUE_DATA(500, "중복된 데이터가 서버 내에 존재합니다.");

    private final int status;
    private int errorCode;
    private final String message;

    ErrorCode(int status, int errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
