package rs.urosvesic.coreservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.ApplicationContext;
import rs.urosvesic.coreservice.repository.MyRepository;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author UrosVesic
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post implements MyEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition="text", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User createdBy;

    @CreationTimestamp
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topicId",referencedColumnName = "id")
    private Topic topic;

    private Instant deletebByAdmin;

    @Override
    public List<MyRepository> returnChildRepositories(ApplicationContext context) {
        MyRepository commentRepository = (MyRepository) context.getBean("commentRepository");
        //MyRepository notificationRepository = (MyRepository) context.getBean("notificationRepository");
        MyRepository reactionRepository = (MyRepository) context.getBean("reactionRepository");
        //MyRepository postReportRepository = (MyRepository) context.getBean("postReportRepository");
        List<MyRepository> list = new ArrayList();
        list.add(commentRepository);
        //list.add(notificationRepository);
        list.add(reactionRepository);
        //list.add(postReportRepository);
        return list;
    }
}
