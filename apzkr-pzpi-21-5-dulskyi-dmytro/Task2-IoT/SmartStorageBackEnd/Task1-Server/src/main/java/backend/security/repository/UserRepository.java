package backend.security.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import backend.security.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesByUsername(String username);

   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

   @Query(value = "Select id From Usr Where usr.username = :username", nativeQuery = true)
   @Transactional
   ArrayList<String> getUserID(String username);

   User findByUsername(String username);

   @Modifying
   @Query(value = "UPDATE usr SET activated = :activated WHERE id = :userId", nativeQuery = true)
   @Transactional
    void banUser(Long userId, Boolean activated);
}
