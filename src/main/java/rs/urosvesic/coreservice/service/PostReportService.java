package rs.urosvesic.coreservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.urosvesic.coreservice.model.Post;
import rs.urosvesic.coreservice.model.PostReport;
import rs.urosvesic.coreservice.model.ReportStatus;
import rs.urosvesic.coreservice.repository.PostReportRepository;
import rs.urosvesic.coreservice.repository.PostRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PostReportService {

    private final PostReportRepository postReportRepository;
    private final PostRepository postRepository;
    @Transactional
    public void changeReportStatus(ReportStatus reportStatus,Long postId) {
        List<PostReport> reports = postReportRepository.findByPost_id(postId);
        reports.forEach(r->{
            if(r.getReportStatus().equals(reportStatus)){
                throw new RuntimeException("Already has status: "+reportStatus);
            }
            if(reportStatus.equals(ReportStatus.APPROVED)){
                Post post = postRepository.findById(postId).orElseThrow((() -> new RuntimeException("Post not found")));
                post.setDeletebByAdmin(null);
                postRepository.save(post);
            }
            r.setReportStatus(reportStatus);
        });
        postReportRepository.saveAll(reports);
    }
}
