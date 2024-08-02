package lk.ijse.gdse66.quantacomit.service.impl;

import lk.ijse.gdse66.quantacomit.dto.UserDTO;
import lk.ijse.gdse66.quantacomit.entity.UserEntity;
import lk.ijse.gdse66.quantacomit.entity.enums.Role;
import lk.ijse.gdse66.quantacomit.repo.UserRepo;
import lk.ijse.gdse66.quantacomit.security.auth.request.SignIn;
import lk.ijse.gdse66.quantacomit.security.auth.request.SignUp;
import lk.ijse.gdse66.quantacomit.security.auth.response.Token;
import lk.ijse.gdse66.quantacomit.service.AuthenticationService;
import lk.ijse.gdse66.quantacomit.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public Token signUp(SignUp signUp) {
        UserDTO userDTO = UserDTO.builder()
                .id(UUID.randomUUID().toString())
                .firstName(signUp.getFirstName())
                .lastName(signUp.getLastName())
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .role(Role.valueOf(signUp.getRole()))
                .build();
        UserEntity savedUser = userRepo.save(mapper.map(userDTO, UserEntity.class));
        String generatedToken = jwtService.generateToken(savedUser);
        return Token.builder().token(generatedToken).build();
    }

    @Override
    public Token signIn(SignIn signIn) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        UserEntity user = userRepo.findByEmail(signIn.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User Not Found...!"));
        String generatedToken = jwtService.generateToken(user);
        return Token.builder().token(generatedToken).build();
    }
}