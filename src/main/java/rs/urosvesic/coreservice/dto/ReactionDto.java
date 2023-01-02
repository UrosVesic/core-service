package rs.urosvesic.coreservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.urosvesic.coreservice.model.ReactionType;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReactionDto implements Dto {
    private Long postId;
    private ReactionType reactionType;
}
