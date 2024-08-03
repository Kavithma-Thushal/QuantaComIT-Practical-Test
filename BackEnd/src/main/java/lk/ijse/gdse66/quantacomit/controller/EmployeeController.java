package lk.ijse.gdse66.quantacomit.controller;

import lk.ijse.gdse66.quantacomit.dto.EmployeeDTO;
import lk.ijse.gdse66.quantacomit.service.EmployeeService;
import lk.ijse.gdse66.quantacomit.util.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : Kavithma Thushal
 * @project : QuantaComIT-Practical-Test
 * @since : 12:37 PM - 8/1/2024
 **/
@CrossOrigin
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    @PostMapping("/saveEmployee")
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        boolean saved = employeeService.saveEmployee(employeeDTO);
        if (saved) {
            String successResponse = "Employee Saved Successfully...!";
            logger.info(successResponse);
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } else {
            String errorResponse = "Duplicate Employee ID Found...!";
            logger.warn(errorResponse);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    @GetMapping("/searchEmployee/{id}")
    public ResponseEntity<?> searchEmployee(@PathVariable String id) {

        EmployeeDTO employeeDTO = employeeService.searchEmployee(id);
        if (employeeDTO != null) {
            String successResponse = "Employee Searched Successfully...!";
            logger.info(successResponse);
            return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
        } else {
            String errorResponse = "Employee Not Found..!";
            logger.error(errorResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<String> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {

        boolean updated = employeeService.updateEmployee(employeeDTO);
        if (updated) {
            String successResponse = "Employee Updated Successfully...!";
            logger.info(successResponse);
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } else {
            String errorResponse = "Employee Not Found...!";
            logger.error(errorResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {

        boolean deleted = employeeService.deleteEmployee(id);
        if (deleted) {
            employeeService.deleteEmployee(id);
            String successResponse = "Employee Deleted Successfully...!";
            logger.info(successResponse);
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } else {
            String errorResponse = "Employee Not Found....!";
            logger.error(errorResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/loadAllEmployees")
    public ResponseEntity<?> loadAllEmployees() {

        List<EmployeeDTO> customerList = employeeService.loadAllEmployees();
        if (customerList != null && !customerList.isEmpty()) {
            String successResponse = "Employees Loaded Successfully...!";
            logger.info(successResponse);
            return ResponseEntity.status(HttpStatus.OK).body(customerList);
        } else {
            String errorResponse = "Employees Not Found...!";
            logger.error(errorResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping("/uploadProfilePicture/{id}")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable String id, @RequestParam("file") MultipartFile file) {

        boolean isUploaded = employeeService.uploadProfilePicture(id, file);
        if (isUploaded) {
            String successResponse = "Profile Picture Uploaded Successfully...!";
            logger.info(successResponse);
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } else {
            String errorResponse = "Failed to upload the profile picture. Employee not found...!";
            logger.warn(errorResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
