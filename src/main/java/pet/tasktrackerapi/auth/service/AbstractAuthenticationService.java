package pet.tasktrackerapi.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pet.tasktrackerapi.api.model.User;
import pet.tasktrackerapi.auth.dto.AuthenticationRequest;
import pet.tasktrackerapi.auth.dto.AuthenticationResponse;
import pet.tasktrackerapi.repository.UserRepository;

public abstract class AbstractAuthenticationService {

    protected final UserRepository userRepository;
    protected final PasswordEncoder passwordEncoder;
    protected final JwtService jwtService;
    protected final AuthenticationManager authenticationManager;

    protected AbstractAuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                            JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) {
        validateCredentials(authenticationRequest);
        User user = findUser(authenticationRequest.getUsername());
        return generateAuthResponse(user);
    }

    protected abstract void validateCredentials(AuthenticationRequest authenticationRequest);

    protected User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    protected AuthenticationResponse generateAuthResponse(User user) {
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
