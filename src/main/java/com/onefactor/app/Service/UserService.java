package com.onefactor.app.Service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.onefactor.app.Entity.User;

public interface UserService {
	User saveUser(User user);

    String validateOtp(String verificationId, String code);
}
