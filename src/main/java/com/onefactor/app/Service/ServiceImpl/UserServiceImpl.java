package com.onefactor.app.Service.ServiceImpl;


import org.springframework.beans.factory.annotation.Autowired;
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
        user.setVerificationId(otpService.sendOtp(user.getPhone()));

    if(userRepository.existsByPhone(user.getPhone())) {
        return userRepository.findByPhone(user.getPhone());
    }
    else {
    	return userRepository.save(user);
    }
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
