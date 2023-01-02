package rs.urosvesic.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.urosvesic.coreservice.model.Comment;
import rs.urosvesic.coreservice.model.MyEntity;
import rs.urosvesic.coreservice.model.Post;

import java.util.List;

/**
 * @author UrosVesic
 */

public interface CommentRepository extends MyRepository, JpaRepository<Comment,Long> {
    
    List<Comment> findAllByPost(Post post);

    @Override
    default void deleteByParent(MyEntity parent) {
        deleteAllByPost((Post) parent);
    }

    void deleteAllByPost(Post post);
    List<Comment> findByPost_idOrderByCreatedDateDesc(Long postId);
}
