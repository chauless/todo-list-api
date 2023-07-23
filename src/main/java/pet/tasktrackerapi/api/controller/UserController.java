package pet.tasktrackerapi.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pet.tasktrackerapi.api.dto.UserDto;
import pet.tasktrackerapi.api.model.User;
import pet.tasktrackerapi.api.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User management", description = "Methods for working with current authorized user")
public class UserController {

    private final UserService userService;

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Getting information about current authorized User", responses = {
            @ApiResponse(responseCode = "200", description =
                    "Getting user id, username and task-list",
                    content = @Content(examples = {@ExampleObject(value = """
                            {
                                "id": 1,
                                "username": "user",
                                "tasks": [
                                    {
                                        "id": "066a2e14-8b0b-45ac-8336-2e57c8da2846",
                                        "title": "example",
                                        "details": "example",
                                        "completed": false
                                    }
                                ]
                            }
                            """)})), @ApiResponse(responseCode = "403", description = "Responds with an Forbidden error if user don't authorized by token", content = @Content())})
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUserInfo(user));
    }
}
