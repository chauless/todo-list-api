package pet.tasktrackerapi.api.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pet.tasktrackerapi.api.dto.UserDto;
import pet.tasktrackerapi.api.model.User;
import pet.tasktrackerapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDto getUserInfo(User user){
        User userEntity = userRepository.findById(user.getId()).orElseThrow();
        return modelMapper.map(userEntity, UserDto.class);
    }

    public User getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
