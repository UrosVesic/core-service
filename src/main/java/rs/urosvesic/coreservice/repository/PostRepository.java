package rs.urosvesic.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.urosvesic.coreservice.model.Post;
import rs.urosvesic.coreservice.model.Topic;

import java.util.List;

/**
 * @author UrosVesic
 */

public interface PostRepository extends JpaRepository<Post, Long> {
    
    List<Post> findByTopicAndDeletebByAdminIsNull(Topic topic);

    List<Post> findAllByCreatedBy_usernameAndDeletebByAdminIsNull(String username);

    List<Post> findByCreatedBy_userIdInAndDeletebByAdminIsNull(List<String> following);

    List<Post> findByTopic_name(String topicName);
}
