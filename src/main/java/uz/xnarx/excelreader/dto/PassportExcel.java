package uz.xnarx.excelreader.dto;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import lombok.Data;

/**
 * @author Samandar Daminov
 * Date: 11/14/2024
 */
@Data
public class PassportExcel {

    @ExcelRow
    private int rowIndex;

    @ExcelCellName("CUSTOMER_NO")
    private String customerNo;
    @ExcelCellName("DATE_OF_BIRTH")
    private String dateOfBirth;
    @ExcelCellName("SERIYA")
    private String serial;
    @ExcelCellName("PASSPORT")
    private String passport;
}
