package lk.ijse.gdse66.quantacomit.service;

import lk.ijse.gdse66.quantacomit.dto.EmployeeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : Kavithma Thushal
 * @project : QuantaComIT-Practical-Test
 * @since : 12:37 PM - 8/1/2024
 **/
public interface EmployeeService {

    boolean saveEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO searchEmployee(String id);

    boolean updateEmployee(EmployeeDTO employeeDTO);

    boolean deleteEmployee(String id);

    List<EmployeeDTO> loadAllEmployees();

    boolean uploadProfilePicture(String id, MultipartFile file);
}
