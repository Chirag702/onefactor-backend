package com.onefactor.app.Service;

import java.util.Optional;


import org.springframework.http.ResponseEntity;

import com.onefactor.app.Entity.User;


public interface UserService {
	User saveUser(User user);

    Object validateOtp(String phone, String code);

	void initProfile(User user);

	Integer getCreditScore(String phone);

	Object getProfileCompletion(String phone);

	Object getUserMaskedDetails(String phone);

 }
