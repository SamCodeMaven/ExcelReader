package uz.xnarx.excelreader.service;

import com.poiji.bind.Poiji;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.xnarx.excelreader.constants.ProjectConstants;
import uz.xnarx.excelreader.dto.PassportExcel;
import uz.xnarx.excelreader.dto.PassportExpiry;
import uz.xnarx.excelreader.enums.ErrorCodes;
import uz.xnarx.excelreader.exception.CustomException;
import uz.xnarx.excelreader.mapper.PassportExpiryMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Samandar Daminov
 * Date: 11/14/2024
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelReaderService {

    public List<PassportExpiry> readExcel(MultipartFile file) {
        validateFile(file);
        List<PassportExpiry> expiries;
        File excelFile;
        try {
            excelFile = File.createTempFile("temp", ".xlsx");
            file.transferTo(excelFile);
            expiries = Poiji.fromExcel(excelFile, PassportExcel.class)
                    .stream()
                    .map(PassportExpiryMapper::toEntity)
                    .toList();
            return expiries;
        } catch (IOException e) {
            throw new CustomException(ErrorCodes.CONVERSION_ERROR);
        }

    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new CustomException(ErrorCodes.INVALID_FILE);
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !(originalFilename.endsWith(".xlsx") || originalFilename.endsWith(".xls"))) {
            throw new CustomException(ErrorCodes.INVALID_FILE);
        }
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new CustomException(ErrorCodes.INVALID_FILE);
            }
            checkDuplicateHeaders(sheet, headerRow);
            checkDuplicateRows(sheet);
        } catch (IOException e) {
            log.error("Error reading xls file", e);
            throw new CustomException(ErrorCodes.INVALID_FILE);
        }
    }

    private void checkDuplicateHeaders(Sheet sheet, Row headerRow) {
        Set<String> headers = new HashSet<>();
        for (Cell cell : headerRow) {
            if (!headers.add(cell.getStringCellValue())) {
                log.warn("Duplicate header '{}'found from excel file", cell.getStringCellValue());
                throw new CustomException(ErrorCodes.DUPLICATE_HEADER);
            }
        }
        if (!headers.contains(ProjectConstants.CUSTOMER_COLUMN)) {
            throw new CustomException(ErrorCodes.CUSTOMER_CODE_COLUMN_NOT_FOUND);
        }
        if (!headers.contains(ProjectConstants.BIRTH_DAY_COLUMN)) {
            throw new CustomException(ErrorCodes.BIRTH_DAY_COLUMN_NOT_FOUND);
        }
        if (!headers.contains(ProjectConstants.SERIAL_COLUMN)) {
            throw new CustomException(ErrorCodes.SERIAL_COLUMN_NOT_FOUND);
        }
        if (!headers.contains(ProjectConstants.NUMBER_COLUMN)) {
            throw new CustomException(ErrorCodes.NUMBER_COLUMN_NOT_FOUND);
        }

        if (sheet.getPhysicalNumberOfRows() > 4000) {
            log.warn("The allowed number of promotional codes has been exceeded {}", sheet.getPhysicalNumberOfRows());
            throw new CustomException(ErrorCodes.LIMIT_EXCEEDED);
        }
    }

    private void checkDuplicateRows(Sheet sheet) {
        if (sheet.getLastRowNum() < 1) {
            throw new CustomException(ErrorCodes.PROMO_CODE_NOT_FOUND_IN_FILE);
        }
        Set<String> uniqueRows = new HashSet<>();
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Cell cell = row.getCell(0);
                String cellValue = getCellValue(cell);
                if (!cellValue.isEmpty() && !uniqueRows.add(cellValue)) {
                    log.warn("Duplicate promo code '{}' found in row {}", cellValue, rowIndex + 1);
                    throw new CustomException(ErrorCodes.DUPLICATE_PROMO_CODE);
                }
            }
        }
    }
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue()); // Convert to int if numeric
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                throw new CustomException(ErrorCodes.CONVERSION_ERROR);
        }
    }
}
