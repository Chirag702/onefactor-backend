package com.onefactor.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import com.onefactor.app.Entity.User;
import com.onefactor.app.Response.ApiResponse;
import com.onefactor.app.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

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
}
