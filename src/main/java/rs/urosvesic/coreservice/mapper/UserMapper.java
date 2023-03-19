package rs.urosvesic.coreservice.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import rs.urosvesic.coreservice.dto.UserDto;
import rs.urosvesic.coreservice.model.User;
import rs.urosvesic.coreservice.repository.UserRepository;
import rs.urosvesic.coreservice.util.UserUtil;


/**
 * @author UrosVesic
 */
@RequiredArgsConstructor
@Component
@Log4j2
public class UserMapper implements GenericMapper<UserDto, User> {

    private final UserUtil userUtil;
    private final UserRepository userRepository;

    @Override
    public User toEntity(UserDto dto) {
        User user = userRepository.findByUsername(dto.getUsername()).get();
        if(user==null){
            user=new User();
        }
        user.setId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setCreated(dto.getCreated());
        user.setBio(dto.getBio());
        user.setEnabled(true);
        return user;
    }

    @Override
    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEnabled(user.isEnabled());
        if(user.isEnabled()) {
            dto.setCreated(user.getCreated());
            dto.setEmail(user.getEmail());
            dto.setFollowedByCurrentUser(user.getFollowers()
                    .stream().map(User::getUsername).toList()
                    .contains(UserUtil.getCurrentUsername()));
            dto.setNumOfFollowers(user.getFollowers().size());
            dto.setNumOfFollowing(user.getFollowing().size());
            dto.setMutualFollowers(user.getMutualFollowers(userUtil.getCurrentUser()));
            dto.setBio(user.getBio());
        }
        return dto;
    }
}
