package rs.urosvesic.coreservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.urosvesic.coreservice.dto.UserDto;
import rs.urosvesic.coreservice.mapper.UserMapper;
import rs.urosvesic.coreservice.model.Following;
import rs.urosvesic.coreservice.model.User;
import rs.urosvesic.coreservice.repository.FollowRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author UrosVesic
 */
@Service
@AllArgsConstructor
public class FollowingService {

    private FollowRepository followRepository;
    private UserMapper userMapper;

    @Transactional
    public List<UserDto> getFollowersForUser(String userId) {
        List<Following> optFoll = followRepository.findAllByFollowed_userId(userId);
        List<User> followers = optFoll.stream().map(Following::getFollowing).toList();
        List<UserDto> followersDto= followers.stream()
                .map((userToMap)->userMapper.toDto(userToMap))
                .toList();
        return followersDto;
    }

    @Transactional
    public List<UserDto> getFollowingForUser(String userId) {
        List<Following> optFoll = followRepository.findAllByFollowing_UserId(userId);
        List<User> followers = optFoll.stream().map(Following::getFollowed).toList();
        List<UserDto> followersDto= followers.stream().map((userToMap)->userMapper.toDto(userToMap)).collect(Collectors.toList());
        return followersDto;
    }
}
