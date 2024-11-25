package uz.xnarx.excelreader.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.xnarx.excelreader.service.PassportService;

/**
 * @author Samandar Daminov
 * Date: 11/15/2024
 */
@RestController
@RequestMapping("/api/passport")
@RequiredArgsConstructor
public class PassportController {
    public final PassportService passportService;

    @PostMapping(path = "/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updatePassport(@RequestParam(value = "file") MultipartFile passportFile) {
        passportService.updatePassport(passportFile);
        return ResponseEntity.noContent().build();
    }
    @PostMapping(path = "/up")
    public ResponseEntity<Void> updatePassport() {
        passportService.retryUpdatePassport();
        return ResponseEntity.noContent().build();
    }
}
