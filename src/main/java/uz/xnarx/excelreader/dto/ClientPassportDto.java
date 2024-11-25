package uz.xnarx.excelreader.dto;

import lombok.Builder;

/**
 * User: javohir.majidov
 * Date: 31/10/24
 */
@Builder
public record ClientPassportDto(
        String clientId,
        String passportSerial,
        String passportNumber,
        String birthDate
) {
}
