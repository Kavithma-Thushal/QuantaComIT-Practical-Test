package lk.ijse.gdse66.quantacomit.service;

import lk.ijse.gdse66.quantacomit.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    void Save(UserDTO userDTO);

    UserDetailsService userDetailService();
}