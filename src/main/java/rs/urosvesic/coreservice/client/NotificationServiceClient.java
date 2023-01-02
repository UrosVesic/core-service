package rs.urosvesic.coreservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import rs.urosvesic.coreservice.dto.NotificationResponse;

@FeignClient(name = "notification-client", url = "${NOTIFICATION_SERVICE_SERVICE_HOST:http://localhost}:8086/api/notification")
public interface NotificationServiceClient {

    @PostMapping
    @Async
    void saveNotification(@RequestBody NotificationResponse notificationResponse,
                  @RequestHeader(value = "Authorization") String authorizationHeader);


}
