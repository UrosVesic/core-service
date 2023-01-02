package rs.urosvesic.coreservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.urosvesic.coreservice.dto.PostRequest;
import rs.urosvesic.coreservice.model.Post;
import rs.urosvesic.coreservice.repository.TopicRepository;
import rs.urosvesic.coreservice.util.UserUtil;

import java.time.Instant;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
@Component
public class PostRequestMapper implements GenericMapper<PostRequest, Post> {

    private final TopicRepository topicRepository;
    private final UserUtil userUtil;
    @Override
    public Post toEntity(PostRequest dto) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setTopic(topicRepository
                .getByName(dto.getTopicName())
                .orElseThrow(()->new RuntimeException("Topic with given name does not exist")));
        post.setCreatedDate(Instant.now());
        post.setCreatedBy(userUtil.getCurrentUser());
        return post;
    }

    @Override
    public PostRequest toDto(Post entity) {
        return null;
    }
}
