package rs.urosvesic.coreservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.urosvesic.coreservice.dto.ReportedUserDto;
import rs.urosvesic.coreservice.model.PostReport;
import rs.urosvesic.coreservice.model.ReportStatus;
import rs.urosvesic.coreservice.model.User;
import rs.urosvesic.coreservice.repository.PostReportRepository;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ReportedUserMapper implements GenericMapper<ReportedUserDto, User> {

    private PostReportRepository postReportRepository;

    @Override
    public User toEntity(ReportedUserDto dto) {
        return null;
    }

    @Override
    public ReportedUserDto toDto(User user) {
        ReportedUserDto reportedUser = new ReportedUserDto();
        reportedUser.setUsername(user.getUsername());
        reportedUser.setNumberOfViolations(countViolations(user));
        reportedUser.setEnabled(user.isEnabled());
        return reportedUser;
    }

    private int countViolations(User user) {
        List<PostReport> reportedPostsOfUser = postReportRepository.findByPost_CreatedByAndReportStatus(user, ReportStatus.DELETED);
        List<PostReport> distinctByPost = reportedPostsOfUser.stream().filter(distinctByPost(PostReport::getPost)).collect(Collectors.toList());
        return distinctByPost.size();
    }

    public <T>Predicate<T> distinctByPost(Function<? super T,?> postExtractor){
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t->seen.add(postExtractor.apply(t));
    }
}
