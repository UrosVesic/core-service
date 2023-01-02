package rs.urosvesic.coreservice.model.idclasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowingId implements Serializable {

    private String following;
    private String followed;

}
