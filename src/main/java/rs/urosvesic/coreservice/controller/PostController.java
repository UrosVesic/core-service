package rs.urosvesic.coreservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import rs.urosvesic.coreservice.dto.CommentDto;
import rs.urosvesic.coreservice.dto.PostRequest;
import rs.urosvesic.coreservice.dto.PostResponse;
import rs.urosvesic.coreservice.dto.ReportedPostDto;
import rs.urosvesic.coreservice.service.CommentService;
import rs.urosvesic.coreservice.service.PostService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author UrosVesic
 */
@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {

    private PostService postservice;
    private CommentService commentService;

    @PostMapping("/{postId}/report")
    public ResponseEntity reportPost(@PathVariable Long postId){
        postservice.reportPost(postId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{postId}/comment")
    public ResponseEntity<List<CommentDto>> getAllCommentsFormPost(@PathVariable Long postId){
        List<CommentDto> commentDtos = commentService.getAllCommentsForPost(postId);
        return new ResponseEntity<>(commentDtos,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        List<PostResponse> allPosts = postservice.getAllPosts();
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @GetMapping("/topic/{topicName}")
    public ResponseEntity<List<PostResponse>> getAllPostsForTopic(@PathVariable String topicName){
        List<PostResponse> topicPosts = postservice.getAllPostsForTopic(topicName);
        return new ResponseEntity<>(topicPosts,HttpStatus.OK);
    }
    @GetMapping("/authAll")
    public ResponseEntity<List<PostResponse>> getAllPostsForFollowingUsers(){
        List<PostResponse> allPosts = postservice.getAllPostsForFollowingUsers();
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @GetMapping("/secured/reported")
    public ResponseEntity<Set<ReportedPostDto>> getAllUnsolvedReportedPosts(){
        Set<ReportedPostDto> reportedPosts= postservice.getAllUnsolvedReportedPosts();
        return new ResponseEntity<>(reportedPosts,HttpStatus.OK);
    }

    @GetMapping("/reported-solved")
    public ResponseEntity<Set<ReportedPostDto>> getAllSolvedPosts(){
        Set<ReportedPostDto> reportedPosts= postservice.getAllSolvedReportedPosts();
        return new ResponseEntity<>(reportedPosts,HttpStatus.OK);
    }

    @PatchMapping("/soft-delete/{id}")
    public ResponseEntity softDeletePost(@PathVariable Long id){
        postservice.softDeletePost(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostRequest postRequest){
        PostResponse postResponse = postservice.createPost(postRequest);
        return new ResponseEntity<>(postResponse,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        PostResponse postResponse = postservice.getPost(id);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostResponse>> getAllPostsForUser(@PathVariable String username){
        List<PostResponse> postResponses = postservice.getAllPostsForUser(username);
        return new ResponseEntity<>(postResponses,HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        postservice.deletePost(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity updatePost(@PathVariable Long id,@RequestBody PostRequest postRequest){
        postservice.updatePost(id,postRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public  ResponseEntity<String> handleMyRuntimeException(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<ObjectError> allErrors = ex.getAllErrors();
        List<String> collect = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(collect,HttpStatus.BAD_REQUEST);
    }

}
