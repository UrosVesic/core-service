package rs.urosvesic.coreservice.mapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Component;
import rs.urosvesic.coreservice.dto.ReportedPostDto;
import rs.urosvesic.coreservice.model.Post;
import rs.urosvesic.coreservice.model.PostReport;
import rs.urosvesic.coreservice.model.ReportStatus;
import rs.urosvesic.coreservice.repository.PostReportRepository;

import java.util.Optional;

/**
 * @author UrosVesic
 */
@Data
@RequiredArgsConstructor
@Component
public class ReportedPostMapper implements GenericMapper<ReportedPostDto, Post> {

    private final PostReportRepository reportRepository;
    @Override
    public Post toEntity(ReportedPostDto dto) {
        return null;
    }

    @Override
    public ReportedPostDto toDto(Post entity) {
       ReportedPostDto dto = new ReportedPostDto();
       dto.setId(entity.getId());
       dto.setContent(entity.getContent());
       dto.setTitle(entity.getTitle());
       PrettyTime p = new PrettyTime();
       dto.setDuration(p.format(entity.getCreatedDate()));
       dto.setTopicname(entity.getTopic().getName());
       dto.setUsername(entity.getCreatedBy().getUsername());
       dto.setReportCount(getReportCount(entity));
       dto.setReportStatus(setReportStatus(entity));
        return dto;
    }

    private ReportStatus setReportStatus(Post post) {
        Optional<PostReport> report = reportRepository.findFirstByPost(post);
        return report.get().getReportStatus();
    }

    private int getReportCount(Post post) {
        return reportRepository.countByPost(post).intValue();
    }
}
