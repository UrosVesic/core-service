package rs.urosvesic.coreservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedUserDto implements Dto{
    private String username;
    private int numberOfViolations;
    private boolean isEnabled;
}
