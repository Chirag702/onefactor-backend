package com.onefactor.app.Service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.onefactor.app.Entity.User;
import com.onefactor.app.Response.ApiResponse;

public interface UserService {
	User saveUser(User user);

    ApiResponse<Object> validateOtp(String phone, String code);

	void initProfile(User user);
}
