package phuongnguyen.movie.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import phuongnguyen.movie.Table.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

    @Query(value = "SELECT * FROM sys_user WHERE " +
        "username = :username OR email = :username",
        nativeQuery = true)
    User findByUsernameOrEmail(String username);

    User findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
