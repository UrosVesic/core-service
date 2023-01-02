package rs.urosvesic.coreservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import rs.urosvesic.coreservice.dto.SaveUserRequest;
import rs.urosvesic.coreservice.dto.UserDto;
import rs.urosvesic.coreservice.service.UserService;
import rs.urosvesic.coreservice.util.UserUtil;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserUtil userUtil;

    @PostMapping
    public ResponseEntity saveUser(@RequestBody SaveUserRequest request){
        userService.save(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> test(){
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = (String) principal.getClaims().get("cognito:username");
        return new ResponseEntity<>(username,HttpStatus.OK);
    }

    @PostMapping(value = "/follow/{username}")
    public ResponseEntity follow(@PathVariable String username){
        userService.follow(username);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    /*@PostMapping("/{username}/assign/{rolename}")
    public ResponseEntity assignRole(@PathVariable String username,@PathVariable String rolename){
        userService.assignRole(username,rolename);
        return new ResponseEntity(HttpStatus.OK);
    }*/

    @PostMapping("/unfollow/{username}")
    public ResponseEntity unfollow(@PathVariable String username){
        userService.unfollow(userUtil.getCurrentUser(), username);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id){
        UserDto userDto = userService.getUser(id);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

   /* @GetMapping("/followers/{userId}")
    public ResponseEntity<List<UserDto>> getAllFollowersForUser(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getAllFollowersForUser(userId),HttpStatus.OK);
    }*/

    @GetMapping("/profile-info/{username}")
    public ResponseEntity<UserDto> getProfileInfo(@PathVariable String username){
        UserDto userResponse=userService.getProfileInfo(username);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }
    @GetMapping("/suggested")
    public ResponseEntity<List<UserDto>> getAllSuggestedUsers(){
        List<UserDto> users = userService.getAllSuggestedUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    /*@GetMapping("/reported")
    public ResponseEntity<List<ReportedUserDto>> getAllReportedUsers(){
        List<ReportedUserDto> reportedUserDtos = userService.getReportedUsers();
        return new ResponseEntity<>(reportedUserDtos,HttpStatus.OK);
    }

    @PatchMapping("/disable/{username}")
    public ResponseEntity disableUser(@PathVariable String username){
        userService.disableUser(username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/enable/{username}")
    public ResponseEntity enable(@PathVariable String username){
        userService.enableUser(username);
        return new ResponseEntity(HttpStatus.OK);
    }*/

    @GetMapping("/{username}/followers")
    public ResponseEntity<List<UserDto>> getAllFollowersForUser(@PathVariable String username){
        return new ResponseEntity<>(userService.getAllFollowersForUser(username),HttpStatus.OK);
    }

    @GetMapping("/{username}/following")
    public ResponseEntity<List<UserDto>> getAllFollowingForUser(@PathVariable String username){
        return new ResponseEntity<>(userService.getAllFollowingForUser(username),HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity updateUser(@RequestBody UserDto userDto){
        userService.updateUser(userDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
