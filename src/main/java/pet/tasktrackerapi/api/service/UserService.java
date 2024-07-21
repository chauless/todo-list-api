package pet.tasktrackerapi.api.service;

import pet.tasktrackerapi.api.dto.UserDto;
import pet.tasktrackerapi.api.model.User;

import java.util.NoSuchElementException;

public interface UserService {
    /**
     * Fetches the user information based on the provided User object.
     * It first retrieves the User entity from the database using the User's id.
     * Then, it maps the User entity to a UserDto object using ModelMapper and returns it.
     *
     * @param user The User object which contains the id of the user to be fetched.
     * @return UserDto The DTO object of the user.
     * @throws NoSuchElementException if no user is found with the provided id.
     */
    UserDto getUserInfo(User user);

    /**
     * Fetches the User entity based on the provided id.
     * It retrieves the User entity from the database using the provided id.
     *
     * @param id The id of the user to be fetched.
     * @return User The User entity.
     * @throws RuntimeException if no user is found with the provided id.
     */
    User getUserEntity(Long id);
}
