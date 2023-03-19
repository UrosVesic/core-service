package rs.urosvesic.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.urosvesic.coreservice.model.*;

import java.util.List;
import java.util.Optional;

/**
 * @author UrosVesic
 */
public interface PostReportRepository extends JpaRepository<PostReport,Long>,MyRepository {

    @Override
    default void deleteByParent(MyEntity parent) {
        deleteAllByPost((Post) parent);
    }

    public void deleteAllByPost(Post post);

    Long countByPost(Post post);

    Optional<PostReport> findFirstByPost(Post post);

    List<PostReport> findAllByReportStatus(ReportStatus unsolved);

    List<PostReport> findAllByReportStatusIn(List<ReportStatus> statuses);

    List<PostReport> findByPost_id(Long postId);

    List<PostReport> findByReportStatus(ReportStatus status);



    List<PostReport> findByPost_CreatedByAndReportStatus(User user, ReportStatus status);
}
