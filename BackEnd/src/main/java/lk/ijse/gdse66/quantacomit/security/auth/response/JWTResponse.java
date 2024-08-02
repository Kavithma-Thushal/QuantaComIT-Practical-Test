package lk.ijse.gdse66.quantacomit.security.auth.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JWTResponse {
    private String token;
    private String role;
}