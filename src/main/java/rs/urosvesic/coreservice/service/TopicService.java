package rs.urosvesic.coreservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.urosvesic.coreservice.dto.TopicDto;
import rs.urosvesic.coreservice.mapper.TopicMapper;
import rs.urosvesic.coreservice.model.Topic;
import rs.urosvesic.coreservice.repository.TopicRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author UrosVesic
 */
@Service
@AllArgsConstructor
public class TopicService {
    private TopicRepository topicRepository;
    private TopicMapper topicMapper;

    @Transactional
    public List<TopicDto> getAllTopics(){
        List<Topic> topics = topicRepository.findAll();
        return topics.stream().map((topic)->topicMapper.toDto(topic)).collect(Collectors.toList());
    }

    @Transactional
    public void createTopic(TopicDto dto) {
        Topic topic = topicMapper.toEntity(dto);
        topicRepository.save(topic);
    }
}
