package rs.urosvesic.coreservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.urosvesic.coreservice.dto.UserDto;
import rs.urosvesic.coreservice.model.User;
import rs.urosvesic.coreservice.util.UserUtil;


/**
 * @author UrosVesic
 */
@RequiredArgsConstructor
@Component
public class UserMapper implements GenericMapper<UserDto, User> {

    private final UserUtil userUtil;

    @Override
    public User toEntity(UserDto dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setCreated(dto.getCreated());
        return user;
    }

    @Override
    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setCreated(user.getCreated());
        dto.setEmail(user.getEmail());
        dto.setFollowedByCurrentUser(user.getFollowers()
                .stream().map(User::getUsername).toList()
                .contains(UserUtil.getCurrentUsername()));
        dto.setNumOfFollowers(user.getFollowers().size());
        dto.setNumOfFollowing(user.getFollowing().size());
        dto.setMutualFollowers(user.getMutualFollowers(userUtil.getCurrentUser()));
        dto.setBio(user.getBio());
        return dto;
    }
}
