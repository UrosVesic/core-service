package rs.urosvesic.coreservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author UrosVesic
 */
@Getter
@Setter
@Entity
public class Topic implements MyEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
