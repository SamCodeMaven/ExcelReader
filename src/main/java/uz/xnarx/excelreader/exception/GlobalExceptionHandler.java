package uz.xnarx.excelreader.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Samandar Daminov
 * Date: 11/14/2024
 */

@Slf4j
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDto> handleCustomException(CustomException ex) {
        log.error("ErrorStatus: {}, Message: {}", ex.getCode(), ex.getMessage());
        return ResponseEntity.status(ex.getCode()).body(ErrorDto.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build());
    }
}
