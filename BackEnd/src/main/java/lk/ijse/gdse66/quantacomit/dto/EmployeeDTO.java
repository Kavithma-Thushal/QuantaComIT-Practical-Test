package lk.ijse.gdse66.quantacomit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : Kavithma Thushal
 * @project : QuantaComIT-Practical-Test
 * @since : 12:37 PM - 8/1/2024
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private String id;
    private String name;
    private String address;
    private String salary;
}
