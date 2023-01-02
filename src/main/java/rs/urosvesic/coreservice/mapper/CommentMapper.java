package rs.urosvesic.coreservice.mapper;

import lombok.RequiredArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Component;
import rs.urosvesic.coreservice.dto.CommentDto;
import rs.urosvesic.coreservice.model.Comment;
import rs.urosvesic.coreservice.repository.PostRepository;
import rs.urosvesic.coreservice.util.UserUtil;

import java.time.Instant;

/**
 * @author UrosVesic
 */
@RequiredArgsConstructor
@Component
public class CommentMapper implements GenericMapper<CommentDto, Comment> {

    private final PostRepository postRepository;
    private final UserUtil userUtil;

    @Override
    public Comment toEntity(CommentDto dto) {
        Comment comment = new Comment();
        comment.setPost(postRepository.findById(dto.getPostId()).orElseThrow(()->new RuntimeException("Post not found")));
        comment.setCreatedDate(Instant.now());
        comment.setText(dto.getText());
        comment.setCreatedBy(userUtil.getCurrentUser());
        return comment;
    }

    @Override
    public CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setPostId(comment.getPost().getId());
        dto.setUsername(comment.getCreatedBy().getUsername());
        dto.setText(comment.getText());
        PrettyTime p = new PrettyTime();
        dto.setDuration(p.format(comment.getCreatedDate()));
        return dto;
    }
}
