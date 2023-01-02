package rs.urosvesic.coreservice.repository;

import org.springframework.stereotype.Repository;
import rs.urosvesic.coreservice.model.MyEntity;

/**
 * @author UrosVesic
 */
@Repository
public interface MyRepository {


     default void deleteByParent(MyEntity parent){}


}
