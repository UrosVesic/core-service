package rs.urosvesic.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.urosvesic.coreservice.model.Topic;

import java.util.Optional;

/**
 * @author UrosVesic
 */
public interface TopicRepository extends JpaRepository<Topic,Long> {
    Optional<Topic> getByName(String topicName);
}
