package lk.ijse.gdse66.quantacomit.controller;

import lk.ijse.gdse66.quantacomit.security.auth.request.SignIn;
import lk.ijse.gdse66.quantacomit.security.auth.request.SignUp;
import lk.ijse.gdse66.quantacomit.security.auth.response.JWTResponse;
import lk.ijse.gdse66.quantacomit.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JWTResponse> signUp(@RequestBody SignUp signUp) {
        return ResponseEntity.ok(authenticationService.signUp(signUp));
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTResponse> signIn(@RequestBody SignIn signIn) {
        return ResponseEntity.ok(authenticationService.signIn(signIn));
    }
}