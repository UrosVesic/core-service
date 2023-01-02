package rs.urosvesic.coreservice.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import rs.urosvesic.coreservice.repository.MyRepository;

import java.util.List;

/**
 * @author UrosVesic
 */
public interface MyEntity {

    default List<MyRepository> returnChildRepositories(@Autowired ApplicationContext context){
        throw new UnsupportedOperationException();
    }
}
