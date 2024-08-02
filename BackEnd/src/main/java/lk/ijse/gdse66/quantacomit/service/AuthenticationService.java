package lk.ijse.gdse66.quantacomit.service;

import lk.ijse.gdse66.quantacomit.security.auth.request.SignIn;
import lk.ijse.gdse66.quantacomit.security.auth.request.SignUp;
import lk.ijse.gdse66.quantacomit.security.auth.response.Token;

public interface AuthenticationService {

    Token signUp(SignUp signUp);

    Token signIn(SignIn signIn);
}