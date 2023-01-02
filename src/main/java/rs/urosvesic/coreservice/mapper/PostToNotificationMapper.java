package rs.urosvesic.coreservice.mapper;

import org.springframework.stereotype.Component;
import rs.urosvesic.coreservice.dto.NotificationResponse;
import rs.urosvesic.coreservice.dto.NotificationType;
import rs.urosvesic.coreservice.model.Post;
import rs.urosvesic.coreservice.model.ReactionType;
import rs.urosvesic.coreservice.util.UserUtil;

@Component
public class PostToNotificationMapper extends GenericResponseMapper<NotificationResponse, Post> {
    @Override
    protected NotificationResponse toDto(Post entity) {
        return NotificationResponse
                .builder()
                .postId(entity.getId())
                .read(false)
                .receiver(entity.getCreatedBy().getUsername())
                .sender(UserUtil.getCurrentUsername())
                .build();
    }

    public NotificationResponse toDto(Post post, ReactionType reactionType){
        NotificationResponse notificationResponse = toDto(post);
        notificationResponse.setNotificationType(setNotificationType(reactionType));
        notificationResponse.setMessage(post
                .getCreatedBy().getUsername()
                + " "
                + convertToString(setNotificationType(reactionType))
                + " "
                + "your post");
        return notificationResponse;
    }

    private NotificationType setNotificationType(ReactionType reactionType) {
        if(reactionType.equals(ReactionType.LIKE)){
            return NotificationType.LIKE;
        }
        return NotificationType.DISLIKE;
    }

    private String convertToString(NotificationType notificationType) {
        return notificationType.toString().toLowerCase()+"d";
    }
}
