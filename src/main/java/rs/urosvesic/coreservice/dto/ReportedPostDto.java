package rs.urosvesic.coreservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.urosvesic.coreservice.model.ReportStatus;

/**
 * @author UrosVesic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedPostDto implements Dto{
    private Long id;
    private String title;
    private String content;
    private String username;
    private String topicname;
    private String duration;
    private int reportCount;
    private ReportStatus reportStatus;
}
