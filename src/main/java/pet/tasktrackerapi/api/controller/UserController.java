package pet.tasktrackerapi.api.controller;

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
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.getUserInfo(user));
    }
}
