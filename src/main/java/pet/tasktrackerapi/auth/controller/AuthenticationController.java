package pet.tasktrackerapi.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "JWT Authentication", description = "Methods for registering and authenticating users")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(description = "Registration of a new user", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Getting JWT-token after successful registration",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "    \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlIiwiaWF0IjoxNjgzMDc2MzUwLCJleHAiOjE2ODMwNzc3OTB9.gg4XpZ7HMqSbCjV4eBw7Wluoe2D23goB68D9gxG-ntM\"\n" +
                                            "}"))),
    })
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        if (authenticationService.userExists(registerRequest.getUsername())){
            throw new UserExistsException();
        }
        return ResponseEntity.ok(authenticationService.register(registerRequest));
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
    })
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        if (!authenticationService.userExists(authenticationRequest.getUsername())){
            throw new BadCredentialsException();
        }
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
