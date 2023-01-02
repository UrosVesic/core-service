package rs.urosvesic.coreservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.urosvesic.coreservice.client.NotificationServiceClient;
import rs.urosvesic.coreservice.dto.NotificationResponse;
import rs.urosvesic.coreservice.mapper.PostToNotificationMapper;
import rs.urosvesic.coreservice.model.Post;
import rs.urosvesic.coreservice.model.ReactionType;
import rs.urosvesic.coreservice.producer.KafkaProducer;
import rs.urosvesic.coreservice.util.UserUtil;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final KafkaProducer kafkaProducer;
    private final PostToNotificationMapper postToNotificationMapper;
    private final NotificationServiceClient notificationClient;
    public void sendNotification(Post post, ReactionType reactionType) {
        if(!post.getCreatedBy().getUsername().equals(UserUtil.getCurrentUsername())){
            NotificationResponse notificationResponse = postToNotificationMapper
                    .toDto(post, reactionType);
            //kafkaProducer.sendNotificationToTopic(notificationResponse);
            notificationClient.saveNotification(notificationResponse, UserUtil.getToken());
        }
    }
}
