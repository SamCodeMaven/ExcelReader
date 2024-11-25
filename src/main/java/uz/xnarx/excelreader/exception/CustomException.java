package uz.xnarx.excelreader.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import uz.xnarx.excelreader.enums.ErrorCodes;


@Getter
public class CustomException extends RuntimeException {
    private final int code;
    private final String message;

    public CustomException(HttpStatusCode code, String message) {
        this.code = code.value();
        this.message = message;
    }

    public CustomException(ErrorCodes error) {
        this.code = error.getStatusCode();
        this.message = error.getMessage();
    }

}
