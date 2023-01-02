package rs.urosvesic.coreservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse implements Dto{

    private Long postId;
    private String sender;
    private String receiver;
    private String message;
    private boolean read;
    private NotificationType notificationType;
}
