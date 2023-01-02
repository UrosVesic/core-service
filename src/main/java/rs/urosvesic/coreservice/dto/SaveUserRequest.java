package rs.urosvesic.coreservice.dto;

import lombok.Data;

@Data
public class SaveUserRequest {

    private String id;
    private String username;
    private String email;

}
