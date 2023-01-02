package rs.urosvesic.coreservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.urosvesic.coreservice.dto.CommentDto;
import rs.urosvesic.coreservice.service.CommentService;

import java.util.List;

/**
 * @author UrosVesic
 */
@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> postComment(@RequestBody CommentDto commentDto){
        commentService.comment(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsFormPost(@PathVariable Long postId){
        List<CommentDto> commentDtos = commentService.getAllCommentsForPost(postId);
        return new ResponseEntity<>(commentDtos,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommend(@PathVariable Long id){
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
