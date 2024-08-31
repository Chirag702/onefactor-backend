package com.onefactor.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import com.onefactor.app.Entity.User;
import com.onefactor.app.Response.ApiResponse;
import com.onefactor.app.Response.Sender;
import com.onefactor.app.Service.UserService;
import com.onefactor.app.Utlities.JWT.JWTUtil;

import io.jsonwebtoken.JwtException;

@RestController
@RequestMapping("/api/send")
public class SendRequestController {

	@Autowired
	private UserService userService;

	@Autowired
	private JWTUtil jwtUtil;

	@PostMapping("details")
	public Sender getMoneySenderDetails(@RequestBody User user, @RequestHeader("Authorization") String tokenHeader) {

		System.out.println("Authorization header: " + tokenHeader);
		try {
			// Extract token from "Bearer " prefix
			String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
			System.out.println("Extracted token: " + token);

			// Extract phone number from token
			String phone = jwtUtil.extractPhone(token);
			System.out.println("Extracted phone number: " + phone);

			if (phone == null || !jwtUtil.validateToken(token, phone)) {

				return null;
			}
			return userService.getMoneySenderDetails(user.getPhone());

		} catch (Exception e) {

			return null;
		}
	}
}