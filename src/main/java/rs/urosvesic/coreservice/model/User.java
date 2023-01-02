package rs.urosvesic.coreservice.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.Instant;
import java.util.List;

/**
 * @author UrosVesic
 */
@Getter
@Setter
@Entity
@Table(name= "myuser")
public class User implements MyEntity {
    @Id
    private String userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Email
    @Column(unique = true, nullable = false)
    private String email;
    private Instant created;
    private String bio;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "following",
            joinColumns = @JoinColumn(name = "following_user_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_user_id"))
    private List<User> following;

    @ManyToMany(mappedBy = "following",fetch = FetchType.LAZY)
    private List<User> followers;


    public int getMutualFollowers(User currentUser) {
        List<User> listOfMutualFoll = followers.stream()
                .filter(two -> currentUser.getFollowers()
                    .stream()
                    .anyMatch(one -> one.getUsername().equals(two.getUsername())))
                .toList();
        return listOfMutualFoll.size();
    }

}
