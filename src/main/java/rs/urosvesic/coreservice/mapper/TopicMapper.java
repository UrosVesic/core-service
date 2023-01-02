package rs.urosvesic.coreservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.urosvesic.coreservice.dto.TopicDto;
import rs.urosvesic.coreservice.model.Topic;
import rs.urosvesic.coreservice.repository.PostRepository;
import rs.urosvesic.coreservice.util.UserUtil;

import java.time.Instant;

/**
 * @author UrosVesic
 */
@RequiredArgsConstructor
@Component
public class TopicMapper implements GenericMapper<TopicDto, Topic> {

    private final PostRepository postRepository;
    private final UserUtil userUtil;

    @Override
    public Topic toEntity(TopicDto dto) {
        Topic topic = new Topic();
        topic.setDescription(dto.getDescription());
        topic.setName(dto.getName());
        topic.setCreatedDate(Instant.now());
        topic.setUser(userUtil.getCurrentUser());
        return topic;
    }

    @Override
    public TopicDto toDto(Topic topic) {
        TopicDto dto = new TopicDto();
        dto.setDescription(topic.getDescription());
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        dto.setNumberOfPosts(postRepository.findByTopicAndDeletebByAdminIsNull(topic).size());
        return dto;
    }
}
