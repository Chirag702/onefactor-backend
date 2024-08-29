package com.onefactor.app.Repository;

 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.onefactor.app.Entity.*;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhone(String phone);
    Optional<User> findByEmail(String email);
	boolean existsByPhone(String phone);
}
