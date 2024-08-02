package lk.ijse.gdse66.quantacomit.security.auth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUp {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}