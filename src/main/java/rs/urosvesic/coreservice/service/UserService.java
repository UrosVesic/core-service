package rs.urosvesic.coreservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.urosvesic.coreservice.client.AuthClient;
import rs.urosvesic.coreservice.dto.ReportedUserDto;
import rs.urosvesic.coreservice.dto.SaveUserRequest;
import rs.urosvesic.coreservice.dto.UserDto;
import rs.urosvesic.coreservice.mapper.ReportedUserMapper;
import rs.urosvesic.coreservice.mapper.UserMapper;
import rs.urosvesic.coreservice.model.Following;
import rs.urosvesic.coreservice.model.PostReport;
import rs.urosvesic.coreservice.model.ReportStatus;
import rs.urosvesic.coreservice.model.User;
import rs.urosvesic.coreservice.repository.FollowRepository;
import rs.urosvesic.coreservice.repository.PostReportRepository;
import rs.urosvesic.coreservice.repository.UserRepository;
import rs.urosvesic.coreservice.util.UserUtil;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final UserMapper userMapper;
    private final UserUtil userUtil;
    private final AuthClient authClient;
    private final PostReportRepository postReportRepository;
    private final ReportedUserMapper reportedUserMapper;

    public void save(SaveUserRequest request) {
        if(!userRepository.existsById(request.getId())){
            User user = new User();
            user.setId(request.getId());
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setEnabled(true);
            userRepository.save(user);
        }
    }



    @Transactional
    public void follow(String username){
        User currentUser = userUtil.getCurrentUser();
        Optional<User> userOptFollowed = userRepository.findByUsername(username);

        User userFollowed = userOptFollowed.orElseThrow(() -> new RuntimeException("User not found"));

        if(userFollowed.getUsername().equals(currentUser.getUsername())){
            throw new RuntimeException("Following not allowed");
        }

        Following following = new Following(currentUser,userFollowed, Instant.now());
        followRepository.save(following);
    }

    @Transactional
    public void unfollow(User currentUser, String username){
        Optional<User> userOptFollowed = userRepository.findByUsername(username);

        User userFollowed = userOptFollowed.orElseThrow(() -> new RuntimeException("User not found"));

        if(userFollowed.getUsername().equals(currentUser.getUsername())){
            throw new RuntimeException("Unfollowing not allowed");
        }

        Following following = new Following(currentUser,userFollowed, null);
        followRepository.delete(following);
    }

    @Transactional
    public UserDto getUser(String id) {
        Optional<User> userOpt = userRepository.findById(id);
        User user = userOpt.orElseThrow(()->new RuntimeException("User with id : "+id+" not found"));
        return userMapper.toDto(user);
    }

    /*public List<UserDto> getAllFollowersForUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        User user = userOpt.orElseThrow(() -> new MyRuntimeException("User not found"));
        List<User> followers = user.getFollowers();
        return followers.stream().map((user1)->userMapper.toDto(user1)).collect(Collectors.toList());
    }*/
    @Transactional
    public List<UserDto> getAllFollowersForUser(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getFollowers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
    @Transactional
    public List<UserDto> getAllFollowingForUser(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.orElseThrow(() -> new RuntimeException("User not found"))
                .getFollowing()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional
    public UserDto getProfileInfo(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }
    @Transactional
    public List<UserDto> getAllSuggestedUsers() {
        //List<User> notFollowing = userRepository.findByUserIdNotInAndIsEnabled(authService.getCurrentUser().getFollowing().stream().map((user)->user.getUserId()).collect(Collectors.toList()),true);
        List<String> ids = userUtil.getCurrentUser().getFollowing()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
        ids.add(UserUtil.getCurrentUserId());
        List<User> notFollowing = userRepository
                .findByIdNotIn(ids)
                .stream()
                .sorted(Comparator.comparingInt(user->user.getMutualFollowers(userUtil.getCurrentUser())))
                .collect(Collectors.toList());
        if(notFollowing.size()==0){
            return Collections.emptyList();
        }
        Collections.reverse(notFollowing);
        return notFollowing.stream().map(userMapper::toDto).toList();
    }
    @Transactional
    public void updateUser(UserDto userDto) {
        if (!UserUtil.getAuthorities().contains("admin") && !userDto.getUsername().equals(UserUtil.getCurrentUsername())){
            throw new RuntimeException("You can update only your account");
        }
        User user = userRepository.findById(userDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        if(userRepository.findByUsername(userDto.getUsername()).isPresent() &&
                !user.getUsername().equals(userDto.getUsername())){
            throw new RuntimeException("Username taken");
        }
        if(userRepository.findByEmail(userDto.getEmail()).isPresent() &&
                !user.getEmail().equals(userDto.getEmail())){
            throw new RuntimeException("Email linked to another account");
        }
        User toUpdate = userMapper.toEntity(userDto);
        userRepository.save(toUpdate);
    }


    @Transactional
    public List<ReportedUserDto> getReportedUsers() {
        List<PostReport> postReports =postReportRepository.findByReportStatus(ReportStatus.DELETED);
        List<User> users = postReports.stream().map(report->report.getPost().getCreatedBy()).collect(Collectors.toList());
        return users.stream().distinct().map(user->reportedUserMapper.toDto(user)).collect(Collectors.toList());
    }

    public void disableUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        authClient.disableUser(username, UserUtil.getToken());
        user.setEnabled(false);
        userRepository.save(user);
    }

    public void enableUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        authClient.enableUser(username, UserUtil.getToken());
        user.setEnabled(true);
        userRepository.save(user);
    }

    public UserDto findUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }
}
