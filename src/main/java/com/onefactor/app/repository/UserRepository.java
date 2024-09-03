package com.onefactor.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.onefactor.app.entity.*;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByPhone(String phone);

	Optional<User> findByEmail(String email);

	boolean existsByPhone(String phone);

	@Query("SELECT u.creditScore FROM User u WHERE u.phone = :phone")
	Integer findCreditScoreByPhone(@Param("phone") String phone);
}
