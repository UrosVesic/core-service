package rs.urosvesic.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.urosvesic.coreservice.model.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);

    List<User> findByIdNotIn(List<String> ids);

    Optional<User> findByEmail(String email);
}
