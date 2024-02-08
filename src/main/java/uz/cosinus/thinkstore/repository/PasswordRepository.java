package uz.cosinus.thinkstore.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
public interface PasswordRepository extends JpaRepository<UserPassword, UUID> {
    @Query(nativeQuery = true, value = """
   SELECT up.* FROM passwords up
                       INNER JOIN users u ON up.user_id = u.id
   WHERE up.code = :password AND u.id = :userId
""")
    Optional<UserPassword> getUserPasswordById(@Param("userId") UUID userId,@Param("password") String password);
}
