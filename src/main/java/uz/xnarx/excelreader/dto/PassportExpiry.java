package uz.xnarx.excelreader.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Samandar Daminov
 * Date: 11/14/2024
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassportExpiry {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String custNo;
        private String serial;
        private String passportNumber;
        private String birthDay;
        private boolean isUpdated;
        private String response;
}
