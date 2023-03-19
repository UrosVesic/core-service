package rs.urosvesic.coreservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.urosvesic.coreservice.dto.TopicDto;
import rs.urosvesic.coreservice.service.TopicService;

import java.util.List;

/**
 * @author UrosVesic
 */
@RestController
@RequestMapping("/api/topic")
@AllArgsConstructor

public class TopicController {

    private TopicService topicService;

    @GetMapping
    public ResponseEntity<List<TopicDto>> getAllTopics(){
        return new ResponseEntity<>(topicService.getAllTopics(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity createTopic(@RequestBody TopicDto topic){
        topicService.createTopic(topic);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public  ResponseEntity<String> handleDataIntegrityViolationException(){
        return new ResponseEntity<>("Topic with given name already exists",HttpStatus.BAD_REQUEST);
    }

}
