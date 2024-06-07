package org.chatapp.chatapp.db.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Boolean existsByEmail(String email);
    Optional<UserEntity> findByEmailAndPassword(String email,String password);
    Optional<UserEntity> findByEmail(String email);
}
