package pet.tasktrackerapi.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pet.tasktrackerapi.api.model.Role;
import pet.tasktrackerapi.api.model.User;
import pet.tasktrackerapi.auth.dto.AuthenticationRequest;
import pet.tasktrackerapi.auth.dto.AuthenticationResponse;
import pet.tasktrackerapi.auth.dto.RegisterRequest;
import pet.tasktrackerapi.exception.BadCredentialsException;
import pet.tasktrackerapi.repository.UserRepository;

import java.util.function.Supplier;

@Service
public class AuthenticationService extends AbstractAuthenticationService {

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 JwtService jwtService, AuthenticationManager authenticationManager) {
        super(userRepository, passwordEncoder, jwtService, authenticationManager);
    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return generateAuthResponse(user);
    }

    @Override
    protected void validateCredentials(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
    }

    public boolean userExists(String username) {
        return userRepository.existsUserByUsername(username);
    }

    public boolean isCredentialsValid(AuthenticationRequest authenticationRequest) {
        String reqUsername = authenticationRequest.getUsername();
        String reqPassword = authenticationRequest.getPassword();
        String dbPassword = userRepository.findByUsername(reqUsername)
                .orElseThrow((Supplier<BadCredentialsException>) BadCredentialsException::new).getPassword();

        return passwordEncoder.matches(reqPassword, dbPassword);
    }
}

