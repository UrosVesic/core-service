package rs.urosvesic.coreservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import rs.urosvesic.coreservice.model.User;
import rs.urosvesic.coreservice.repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public static Jwt getPrincipal(){ return  (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();}

    public static String getToken(){ return "Bearer "+getPrincipal().getTokenValue(); }
    public static String getCurrentUsername(){
        return getPrincipal().getClaimAsString("username");
    }

    public static String getCurrentUserId(){
        return getPrincipal().getClaimAsString("sub");
    }
    public static List<String> getAuthorities(){
        return getPrincipal().getClaimAsStringList("cognito:groups");
    }


    public User getCurrentUser() {
        return userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(()->new RuntimeException("User not found"));
    }
}
