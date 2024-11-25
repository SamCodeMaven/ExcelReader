package uz.xnarx.excelreader.mapper;

import uz.xnarx.excelreader.dto.ClientPassportDto;
import uz.xnarx.excelreader.dto.PassportExcel;
import uz.xnarx.excelreader.dto.PassportExpiry;

/**
 * @author Samandar Daminov
 * Date: 11/15/2024
 */

public class PassportExpiryMapper {
    public static PassportExpiry toEntity(PassportExcel passportExcel){
        return PassportExpiry.builder()
                .custNo(passportExcel.getCustomerNo())
                .serial(passportExcel.getSerial())
                .passportNumber(passportExcel.getPassport())
                .birthDay(passportExcel.getDateOfBirth())
                .build();
    }

    public static ClientPassportDto toDto(PassportExpiry passportExpiry){
        return ClientPassportDto.builder()
                .clientId(passportExpiry.getCustNo())
                .passportSerial(passportExpiry.getSerial())
                .passportNumber(passportExpiry.getPassportNumber())
                .birthDate(passportExpiry.getBirthDay())
                .build();
    }
}
