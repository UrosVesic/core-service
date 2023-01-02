package rs.urosvesic.coreservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.urosvesic.coreservice.service.FollowingService;

/**
 * @author UrosVesic
 */
@RestController
@RequestMapping("/api/follow")
@AllArgsConstructor
public class FollowingController {

    private FollowingService followingService;

//    @GetMapping("/{userId}/followers")
//    public List<UserDto> getFollowersForUser(@PathVariable String userId){
//        return followingService.getFollowersForUser(userId);
//    }
//
//    @GetMapping("/{userId}/following")
//    public List<UserDto> getFollowingForUser(@PathVariable String userId){
//        return followingService.getFollowersForUser(userId);
//    }
}
