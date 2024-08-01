package lk.ijse.gdse66.quantacomit.service.impl;

import lk.ijse.gdse66.quantacomit.dto.EmployeeDTO;
import lk.ijse.gdse66.quantacomit.entity.EmployeeEntity;
import lk.ijse.gdse66.quantacomit.repo.EmployeeRepo;
import lk.ijse.gdse66.quantacomit.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        List<EmployeeEntity> customers = employeeRepo.findAll();
        return customers.stream().map(customer -> modelMapper.map(customer, EmployeeDTO.class)).collect(Collectors.toList());
    }
}
