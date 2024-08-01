package lk.ijse.gdse66.quantacomit.repo;

import lk.ijse.gdse66.quantacomit.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Kavithma Thushal
 * @project : QuantaComIT-Practical-Test
 * @since : 12:37 PM - 8/1/2024
 **/
public interface EmployeeRepo extends JpaRepository<EmployeeEntity, String> {
}
