package rs.urosvesic.coreservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/**
 * @author UrosVesic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto implements Dto{

    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    private Integer numberOfPosts;
}
