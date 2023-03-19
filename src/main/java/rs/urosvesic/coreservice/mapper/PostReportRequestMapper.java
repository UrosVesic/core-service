package rs.urosvesic.coreservice.mapper;

import org.springframework.stereotype.Component;
import rs.urosvesic.coreservice.model.PostReport;
import rs.urosvesic.coreservice.model.ReportStatus;
import rs.urosvesic.coreservice.repository.PostRepository;
import rs.urosvesic.coreservice.repository.UserRepository;
import rs.urosvesic.coreservice.util.UserUtil;

/**
 * @author UrosVesic
 */
@Component
public class PostReportRequestMapper{

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostReportRequestMapper(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostReport toEntity(Long id) {
        PostReport postReport = new PostReport();
        postReport.setPost(postRepository.findById(id).orElseThrow(()->new RuntimeException("Post does not exist")));
        postReport.setUser(userRepository
                .findById(UserUtil.getCurrentUserId())
                .orElseThrow(()->new RuntimeException("User does not exist")));
        postReport.setReportStatus(ReportStatus.UNSOLVED);
        return postReport;
    }

}
