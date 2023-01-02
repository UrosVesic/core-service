package rs.urosvesic.coreservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/**
 * @author UrosVesic
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostRequest implements Dto {

    @NotBlank(message = "Title is required")
    String title;
    @NotBlank(message = "Topic is required")
    String topicName;
    @NotBlank(message = "Content is required")
    String content;

}
