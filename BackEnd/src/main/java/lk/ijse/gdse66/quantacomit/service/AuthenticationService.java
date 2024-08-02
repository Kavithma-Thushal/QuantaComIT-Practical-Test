package lk.ijse.gdse66.quantacomit.service;

import lk.ijse.gdse66.quantacomit.security.auth.request.SignIn;
import lk.ijse.gdse66.quantacomit.security.auth.request.SignUp;
import lk.ijse.gdse66.quantacomit.security.auth.response.JWTResponse;

public interface AuthenticationService {

    JWTResponse signUp(SignUp signUp);

    JWTResponse signIn(SignIn signIn);
}