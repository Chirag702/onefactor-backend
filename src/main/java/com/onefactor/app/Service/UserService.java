package com.onefactor.app.Service;

import java.util.Optional;

import com.onefactor.app.Entity.User;

public interface UserService {
	User saveUser(User user);

	User findByPhone(String phone);

	Optional<User> findByEmail(String email);

	Optional<User> findById(Long id);

	void deleteUser(Long id);
}
