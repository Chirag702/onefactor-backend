package com.onefactor.app.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.onefactor.app.Entity.User;
import com.onefactor.app.Repository.UserRepository;
import com.onefactor.app.Response.ApiResponse;
import com.onefactor.app.Service.UserService;
import com.onefactor.app.Utlities.JWT.JWTUtil;
import com.onefactor.app.Utlities.OTP.OtpService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OtpService otpService;

	@Override
	public User saveUser(User user) {
		try {
			user.setVerificationId(otpService.sendOtp(user.getPhone()));

			if (userRepository.existsByPhone(user.getPhone())) {
				User user2 = userRepository.findByPhone(user.getPhone());
				user2.setVerificationId(user.getVerificationId());
				return userRepository.save(user2);
			} else {
				user.setCreditScore(650);
				return userRepository.save(user);
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ApiResponse<Object> validateOtp(String phone, String code) {
		String verificationId = userRepository.findByPhone(phone).getVerificationId();
		System.out.println("Verification ID: " + verificationId);
		return otpService.validateOtp(phone, code, verificationId);
	}

	@Override
	public void initProfile(User user) {
		User user2 = userRepository.findByPhone(user.getPhone());
		if (user.getFirstName() != null) {
			user2.setFirstName(user.getFirstName());
		}
		if (user.getLastName() != null) {
			user2.setLastName(user.getLastName());
		}
		if (user.getEmail() != null) {
			user2.setEmail(user.getEmail());
		}

		if (user.getPan() != null) {
			user2.setPan(user.getPan());
		}
		if (user.getDob() != null) {
			user2.setDob(user.getDob());
		}
		if (user.getGender() != null) {
			user2.setGender(user.getGender());
		}

		if (user.getHno() != null) {
			user2.setHno(user.getHno());
		}

		if (user.getBuilding() != null) {
			user2.setBuilding(user.getBuilding());
		}

		if (user.getArea() != null) {
			user2.setArea(user.getArea());
		}
		if (user.getPinCode() != null) {
			user2.setPinCode(user.getPinCode());
		}

		userRepository.save(user2);

	}

	@Override
	public Integer getCreditScore(String phone) {
		return userRepository.findCreditScoreByPhone(phone);
	}

	@Override
	public Map<String, Boolean> getProfileCompletion(String phone) {
		User user = userRepository.findByPhone(phone);
		Map<String, Boolean> profile = new HashMap<>();

		// Check various fields and add to the map
		profile.put("firstName", user.getFirstName() != null && !user.getFirstName().isEmpty());
		profile.put("lastName", user.getLastName() != null && !user.getLastName().isEmpty());
		profile.put("email", user.getEmail() != null && !user.getEmail().isEmpty());
		profile.put("pan", user.getPan() != null && !user.getPan().isEmpty());
		profile.put("dob", user.getDob() != null && !user.getDob().isEmpty());
		profile.put("gender", user.getGender() != null && !user.getGender().isEmpty());
		profile.put("hno", user.getHno() != null && !user.getHno().isEmpty());
		profile.put("building", user.getBuilding() != null && !user.getBuilding().isEmpty());
		profile.put("area", user.getArea() != null && !user.getArea().isEmpty());
		profile.put("pinCode", user.getPinCode() != null && !user.getPinCode().isEmpty());

		return profile;
	}
}
