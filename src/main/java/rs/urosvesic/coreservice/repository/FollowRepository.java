package rs.urosvesic.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.urosvesic.coreservice.model.Following;
import rs.urosvesic.coreservice.model.idclasses.FollowingId;

import java.util.List;

/**
 * @author UrosVesic
 */
public interface FollowRepository extends JpaRepository<Following, FollowingId> {

    List<Following> findAllByFollowed_Id(String id);
    List<Following> findAllByFollowing_Id(String id);
}
