package pet.tasktrackerapi.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pet.tasktrackerapi.auth.dto.AuthenticationRequest;
import pet.tasktrackerapi.auth.dto.AuthenticationResponse;
import pet.tasktrackerapi.auth.dto.RegisterRequest;
import pet.tasktrackerapi.auth.service.AuthenticationService;
import pet.tasktrackerapi.exception.BadCredentialsException;
import pet.tasktrackerapi.exception.UserExistsException;
import pet.tasktrackerapi.rabbitmq.producer.RabbitMessageSender;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "JWT Authentication", description = "Methods for registering and authenticating users")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RabbitMessageSender rabbitMessageSender;

    @PostMapping("/register")
    @Operation(description = "Registration of a new user", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Getting JWT-token after successful registration",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "    \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlIiwiaWF0IjoxNjgzMDc2MzUwLCJleHAiOjE2ODMwNzc3OTB9.gg4XpZ7HMqSbCjV4eBw7Wluoe2D23goB68D9gxG-ntM\"\n" +
                                            "}"))),
            @ApiResponse(responseCode = "409",
                    description = "Responds with an Conflict error if username is taken",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "message": "This username is already taken!"
                                                    }
                                                    """ )}))})
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest registerRequest) throws JsonProcessingException {
        if (authenticationService.userExists(registerRequest.getUsername())){
            throw new UserExistsException();
        }

        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);
//        rabbitMessageSender.sendWelcomeEmail(registerRequest.getUsername());

        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping(value = "/authenticate")
    @Operation(description = "Authentication of a user", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Getting JWT-token after successful authentication",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "    \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlIiwiaWF0IjoxNjgzMDc2MzUwLCJleHAiOjE2ODMwNzc3OTB9.gg4XpZ7HMqSbCjV4eBw7Wluoe2D23goB68D9gxG-ntM\"\n" +
                                            "}"))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Responds with an Unauthorized error if username/password is invalid",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                            {
                                                                "message": "Bad credentials!"
                                                            }
                                                            """ )}))})
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        if (!authenticationService.isCredentialsValid(authenticationRequest)) {
            throw new BadCredentialsException();
        }
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
