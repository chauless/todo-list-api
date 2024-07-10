package pet.tasktrackerapi.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pet.tasktrackerapi.auth.controller.AuthenticationController;
import pet.tasktrackerapi.auth.dto.AuthenticationRequest;
import pet.tasktrackerapi.auth.dto.AuthenticationResponse;
import pet.tasktrackerapi.auth.dto.RegisterRequest;
import pet.tasktrackerapi.auth.service.AuthenticationService;
import pet.tasktrackerapi.exception.BadCredentialsException;
import pet.tasktrackerapi.exception.UserExistsException;
import pet.tasktrackerapi.rabbitmq.producer.RabbitMessageSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private RabbitMessageSender rabbitMessageSender;

    private AuthenticationController authenticationController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        authenticationController = new AuthenticationController(authenticationService, rabbitMessageSender);
    }

    @Test
    void testRegisterSuccessful() throws Exception {

        RegisterRequest registerRequest = new RegisterRequest.Builder()
                .username("testuser")
                .password("testpassword")
                .build();

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken("test_token");

        // When
        when(authenticationService.userExists("testuser")).thenReturn(false);
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(authenticationResponse);

        ResponseEntity<AuthenticationResponse> response = authenticationController.register(registerRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationResponse.getToken(), response.getBody().getToken());
    }

    @Test
    void testRegisterUserExists() throws Exception {
        // Given
        RegisterRequest registerRequest = new RegisterRequest.Builder()
                .username("existinguser")
                .password("testpassword")
                .build();

        // When
        when(authenticationService.userExists("existinguser")).thenReturn(true);

        // Then
        assertThrows(UserExistsException.class, () -> authenticationController.register(registerRequest));
    }

    @Test
    void testAuthenticateSuccessful() {
        // Given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("testuser");
        authenticationRequest.setPassword("testpassword");

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken("test_token");

        // When
        when(authenticationService.isCredentialsValid(authenticationRequest)).thenReturn(true);
        when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenReturn(authenticationResponse);

        ResponseEntity<AuthenticationResponse> response = authenticationController.authenticate(authenticationRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationResponse.getToken(), response.getBody().getToken());
    }

    @Test
    void testAuthenticateInvalidCredentials() {
        // Given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("testuser");
        authenticationRequest.setPassword("testpassword");

        // When
        when(authenticationService.isCredentialsValid(authenticationRequest)).thenReturn(false);

        // Then
        assertThrows(BadCredentialsException.class, () -> authenticationController.authenticate(authenticationRequest));
    }
}