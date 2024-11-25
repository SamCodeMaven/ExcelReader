package uz.xnarx.excelreader.exception;

import lombok.Builder;

@Builder
public record ErrorDto(
        int code,
        String message) {
}
