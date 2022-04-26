package demo.Repository;

import demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    List<User> findUserBy();

    User findUserByActivationCode(String code);
}

