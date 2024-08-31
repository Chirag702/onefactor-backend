package com.onefactor.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import com.onefactor.app.Entity.User;
import com.onefactor.app.Response.ApiResponse;
import com.onefactor.app.Service.UserService;
import com.onefactor.app.Utlities.JWT.JWTUtil;

import io.jsonwebtoken.JwtException;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<String>> saveUser(@RequestBody User user) {
		try {
			userService.saveUser(user);
			ApiResponse<String> response = new ApiResponse<>(200, null, "User saved and OTP sent");
			return ResponseEntity.ok(response);
		} catch (HttpClientErrorException e) {
			ApiResponse<String> response = new ApiResponse<>(e.getStatusCode().value(), e.getResponseBodyAsString(),
					null);
			return ResponseEntity.status(e.getStatusCode()).body(response);
		} catch (Exception e) {
			ApiResponse<String> response = new ApiResponse<>(500, "Internal Server Error", null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		} finally {
			// Any cleanup code or necessary final steps can go here.
			// If no action is needed, this can be left empty.
		}
	}

	@PostMapping("/validateOtp")
	public ResponseEntity<ApiResponse<Object>> validateOtp(@RequestParam String phone, @RequestParam String code) {
		try {
			// Call service to validate OTP
			ApiResponse<Object> result = userService.validateOtp(phone, code);

			// Return the response
			return ResponseEntity.status(result.getStatusCode()).body(result);
		} catch (HttpClientErrorException e) {
			// Handle HTTP client errors
			ApiResponse<Object> errorResponse = new ApiResponse<>(e.getStatusCode().value(),
					"Client Error: " + e.getResponseBodyAsString(), null);
			return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
		} catch (Exception e) {
			// Handle generic exceptions
			ApiResponse<Object> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Internal Server Error: " + e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/profile/init")
	public ResponseEntity<ApiResponse<String>> initProfile(@RequestBody User user,
			@RequestHeader("Authorization") String tokenHeader) {

		System.out.println("Authorization header: " + tokenHeader);
		try {
			// Extract token from "Bearer " prefix
			String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
			System.out.println("Extracted token: " + token);

			// Extract phone number from token
			String phone = jwtUtil.extractPhone(token);
			System.out.println("Extracted phone number: " + phone);

			if (phone == null || !jwtUtil.validateToken(token, phone)) {
				// Token is invalid or expired
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new ApiResponse<>(403, "Access denied. Invalid or expired token", null));
			}

			user.setPhone(phone);
			userService.initProfile(user);
			ApiResponse<String> response = new ApiResponse<>(200, null, "User profile created");
			return ResponseEntity.ok(response);
		} catch (JwtException e) {
			// Token related issues
			ApiResponse<String> response = new ApiResponse<>(403, "Access denied. Invalid token", null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		} catch (Exception e) {
			// General errors
			ApiResponse<String> response = new ApiResponse<>(500, "Internal Server Error", null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/creditScore")
	public ResponseEntity<ApiResponse<Integer>> getCreditScore(@RequestHeader("Authorization") String tokenHeader) {
		System.out.println("Authorization header: " + tokenHeader);
		try {
			// Extract token from "Bearer " prefix
			String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
			System.out.println("Extracted token: " + token);

			// Extract phone number from token
			String phone = jwtUtil.extractPhone(token);
			System.out.println("Extracted phone number: " + phone);

			if (phone == null || !jwtUtil.validateToken(token, phone)) {
				// Token is invalid or expired
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new ApiResponse<>(403, "Access denied. Invalid or expired token", null));
			}

			// Retrieve the credit score
			Integer creditScore = userService.getCreditScore(phone);

			// Return the credit score in the response
			ApiResponse<Integer> response = new ApiResponse<>(200, "Credit score retrieved successfully", creditScore);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace(); // Log the exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(500, "An unexpected error occurred", null));
		}
	}

	@GetMapping("profile/exists")
	public ResponseEntity<ApiResponse<Object>> checkProfileExistance(
			@RequestHeader("Authorization") String tokenHeader) {
		System.out.println("Authorization header: " + tokenHeader);
		try {
			// Extract token from "Bearer " prefix
			String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
			System.out.println("Extracted token: " + token);

			// Extract phone number from token
			String phone = jwtUtil.extractPhone(token);
			System.out.println("Extracted phone number: " + phone);

			if (phone == null || !jwtUtil.validateToken(token, phone)) {
				// Token is invalid or expired
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new ApiResponse<>(403, "Access denied. Invalid or expired token", null));
			}

			Object profile = userService.getProfileCompletion(phone);
			ApiResponse<Object> response = new ApiResponse<>(200, "Checking profile", profile);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace(); // Log the exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(500, "An unexpected error occurred", null));
		}
	}

}