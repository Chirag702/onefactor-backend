package com.onefactor.app.service;

import java.util.List;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.onefactor.app.entity.User;

public interface UserService {
	User saveUser(User user);

	Object validateOtp(String phone, String code);

	void initProfile(User user);

	Integer getCreditScore(String phone);

	Object getProfileCompletion(String phone);

	Object getUserMaskedDetails(String phone);

	User createUser(User user);

	List<User> getAllUsers();

	User getUserById(Long id);

}
