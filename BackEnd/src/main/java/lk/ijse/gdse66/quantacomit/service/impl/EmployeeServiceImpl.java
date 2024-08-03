package lk.ijse.gdse66.quantacomit.service.impl;

import lk.ijse.gdse66.quantacomit.dto.EmployeeDTO;
import lk.ijse.gdse66.quantacomit.entity.EmployeeEntity;
import lk.ijse.gdse66.quantacomit.repo.EmployeeRepo;
import lk.ijse.gdse66.quantacomit.service.EmployeeService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Kavithma Thushal
 * @project : QuantaComIT-Practical-Test
 * @since : 12:37 PM - 8/1/2024
 **/
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper modelMapper;

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepo.existsById(employeeDTO.getId())) {
            // If customer already exists, return false
            return false;
        } else {
            // If customer does not exist, save the new customer
            employeeRepo.save(modelMapper.map(employeeDTO, EmployeeEntity.class));
            return true;
        }
    }

    @Override
    public EmployeeDTO searchEmployee(String id) {
        return employeeRepo.findById(id).map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class)).orElse(null);
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepo.existsById(employeeDTO.getId())) {
            // If customer exist, update the customer and return true
            employeeRepo.save(modelMapper.map(employeeDTO, EmployeeEntity.class));
            return true;
        } else {
            // If customer does not exist return false
            return false;
        }
    }

    @Override
    public boolean deleteEmployee(String id) {
        if (employeeRepo.existsById(id)) {
            employeeRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<EmployeeDTO> loadAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepo.findAll();
        return employeeEntities.stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean uploadProfilePicture(String id, MultipartFile file) {
        if (employeeRepo.existsById(id)) {
            String fileName = id + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            try {
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());
                return true;
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload profile picture : " + e.getMessage(), e);
            }
        } else {
            return false;
        }
    }

    @Override
    public byte[] downloadProfilePicture(String id) {
        if (employeeRepo.existsById(id)) {
            String fileName = id + "_";
            try {
                Path dir = Paths.get(UPLOAD_DIR);
                Path matchingFile = Files.list(dir)
                        .filter(file -> file.getFileName().toString().startsWith(fileName))
                        .findFirst()
                        .orElse(null);
                if (matchingFile != null) {
                    return Files.readAllBytes(matchingFile);
                } else {
                    return null;
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to download profile picture : " + e.getMessage(), e);
            }
        } else {
            return null;
        }
    }

    @Override
    public byte[] exportEmployeeReport() {
        try {
            // Load employee data
            List<EmployeeDTO> employees = loadAllEmployees();

            // Load the report file from classpath
            Resource resource = new ClassPathResource("employees.jrxml");
            File reportFile = resource.getFile();

            // Compile the Jasper report from .jrxml to .jasper
            String jasperReportPath = reportFile.getAbsolutePath().replace(".jrxml", ".jasper");
            JasperCompileManager.compileReportToFile(reportFile.getAbsolutePath(), jasperReportPath);

            // Load the compiled Jasper report
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReportPath,
                    new HashMap<>(),
                    new JRBeanCollectionDataSource(employees));

            // Export the report to PDF format
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
                return baos.toByteArray();
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF report: " + e.getMessage(), e);
        }
    }
}
