package uz.xnarx.excelreader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.xnarx.excelreader.caller.ClientCaller;
import uz.xnarx.excelreader.dto.ClientPassportDto;
import uz.xnarx.excelreader.dto.PassportExpiry;
import uz.xnarx.excelreader.mapper.PassportExpiryMapper;
import uz.xnarx.excelreader.repository.PassportRepository;

import java.util.List;

/**
 * @author Samandar Daminov
 * Date: 11/15/2024
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class PassportService {

    private final PassportRepository passportRepository;
    private final ExcelReaderService excelService;
    private final ClientCaller clientCaller;

    public void updatePassport(MultipartFile multipartFile) {
        log.info("Updating passport started");
        log.info("Excel reading started");
        List<PassportExpiry> passports = excelService.readExcel(multipartFile);
        log.info("Excel reading finished");

        passports.forEach(passportExpiry -> {
            ClientPassportDto clientPassportDto = PassportExpiryMapper.toDto(passportExpiry);
            String updateResponse = clientCaller.updateClientPassport(clientPassportDto, "testTraceId");

            boolean isUpdated = "Success".equals(updateResponse);
            passportExpiry.setUpdated(isUpdated);
            passportExpiry.setResponse(updateResponse);

            log.info("Updated passport: {}, status: {} " , passportExpiry.getCustNo(), isUpdated);
            passportRepository.save(passportExpiry);
        });
    }

    public void retryUpdatePassport() {
        log.info("Retrying update passport started");
        List<PassportExpiry> passports = passportRepository.findByUpdated();
        passports.forEach(passportExpiry -> {
                    ClientPassportDto clientPassportDto = PassportExpiryMapper.toDto(passportExpiry);
                    String updateResponse = clientCaller.updateClientPassport(clientPassportDto, "testTraceId");

                    boolean isUpdated = "Success".equals(updateResponse);
                    passportExpiry.setUpdated(isUpdated);
                    passportExpiry.setResponse(updateResponse);

                    log.info("Updated passport:{} status: {} " , passportExpiry.getCustNo(), isUpdated);
                    passportRepository.save(passportExpiry);
                }
        );
    }
}
