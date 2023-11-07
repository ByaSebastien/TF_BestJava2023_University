package be.bstorm.repositories.security;

import be.bstorm.models.security.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT u FROM User u WHERE u.username = :username")
    Optional<UserEntity> findOneByUsername(@Param("username") String username);
}
