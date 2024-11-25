package uz.xnarx.excelreader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.xnarx.excelreader.dto.PassportExpiry;

import java.util.List;

/**
 * @author Samandar Daminov
 * Date: 11/15/2024
 */

public interface PassportRepository extends JpaRepository<PassportExpiry, Long> {

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM passport_expiry AS p " +
            "WHERE p.is_updated = false " +
            "ORDER BY p.id ASC " +
            "LIMIT 100")
    List<PassportExpiry> findByUpdated();
}
