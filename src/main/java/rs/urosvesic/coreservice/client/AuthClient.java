package rs.urosvesic.coreservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(name = "core-client", url = "${CORE_SERVICE_SERVICE_HOST:http://localhost}:8083/api/user")
@FeignClient(name = "AUTH-SERVICE")
public interface AuthClient {

    @PostMapping("/api/cognito/disable/{username}")
    void disableUser(@PathVariable String username, @RequestHeader(value = "Authorization") String authorizationHeader);

    @PostMapping("/api/cognito/enable/{username}")
    void enableUser(@PathVariable String username, @RequestHeader(value = "Authorization") String authorizationHeader);
}
