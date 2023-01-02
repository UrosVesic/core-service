package rs.urosvesic.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.urosvesic.coreservice.model.MyEntity;
import rs.urosvesic.coreservice.model.Post;
import rs.urosvesic.coreservice.model.Reaction;
import rs.urosvesic.coreservice.model.ReactionType;

import java.util.List;
import java.util.Optional;

/**
 * @author UrosVesic
 */

public interface ReactionRepository extends JpaRepository<Reaction,Long>, MyRepository {

    @Override
    default void deleteByParent(MyEntity parent) {
        deleteAllByPost((Post) parent);
    }

    void deleteAllByPost(Post post);

    List<Reaction> findByPostAndReactionType(Post post, ReactionType like);

    Optional<Reaction> findByPost_idAndCreatedBy_Username(Long postId, String createdBy);
}
