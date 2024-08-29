package com.onefactor.app.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.onefactor.app.Entity.User;
import com.onefactor.app.Repository.UserRepository;
import com.onefactor.app.Service.UserService;
import com.onefactor.app.Utlities.OTP.OtpService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OtpService otpService;

	@Override
	public User saveUser(User user) {
		try {
			user.setVerificationId(otpService.sendOtp(user.getPhone()));

			if (userRepository.existsByPhone(user.getPhone())) {
				User user2=userRepository.findByPhone(user.getPhone());
				user2.setVerificationId(user.getVerificationId());
				return userRepository.save(user2);
			} else {
				return userRepository.save(user);
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String validateOtp(String phone, String code) {
		String verificationId = userRepository.findByPhone(phone).getVerificationId();
		System.out.println(verificationId);
		return otpService.validateOtp(phone, code, verificationId);
	}
}
