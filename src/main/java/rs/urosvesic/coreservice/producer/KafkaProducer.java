package rs.urosvesic.coreservice.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import rs.urosvesic.coreservice.dto.NotificationResponse;

@RequiredArgsConstructor
@Component
public class KafkaProducer {

    private final KafkaTemplate<String, NotificationResponse> kafkaTemplate;

    @Value("${producer.topic.name}")
    private String topicName;

    public void sendNotificationToTopic(NotificationResponse notificationResponse){
        kafkaTemplate.send(topicName, notificationResponse);
    }
}
