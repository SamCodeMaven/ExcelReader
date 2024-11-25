package uz.xnarx.excelreader.enums;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    INVALID_FILE(400, "Invalid file. Please correct the file and try again."),
    DUPLICATE_HEADER(400, "Duplicate column header found. Please correct the Excel file and try again."),
    CUSTOMER_CODE_COLUMN_NOT_FOUND(400,"File does not contain 'CUSTOMER_NO' column"),
    BIRTH_DAY_COLUMN_NOT_FOUND(400,"File does not contain 'DATE_OF_BIRTH' column"),
    SERIAL_COLUMN_NOT_FOUND(400,"File does not contain 'SERIYA' column"),
    NUMBER_COLUMN_NOT_FOUND(400,"File does not contain 'PASSPORT' column"),
    LIMIT_EXCEEDED(400, "The allowed number of promotional codes has been exceeded (maximum: 500.000)"),
    DUPLICATE_PROMO_CODE(400, "The file contains duplicate promo codes. Please correct file."),
    PROMO_CODE_NOT_FOUND_IN_FILE(400,"File does not contain any promoCode"),
    REF_CODE_NOT_FOUND(404,"RefCode not found"),
    CONVERSION_ERROR(404,"CONVERSION ERROR");
    ErrorCodes(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    private final int statusCode;
    private final String message;
}
