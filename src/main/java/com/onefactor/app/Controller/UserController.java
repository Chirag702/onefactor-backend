package com.onefactor.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import com.onefactor.app.Entity.User;
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
    public String saveUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return "User saved and OTP sent";
        } catch (HttpClientErrorException e) {
            return e.getResponseBodyAsString();
        } catch (Exception e) {
            return "Internal Server Error";
        }
    }

    @PostMapping("/validateOtp")
    public Object validateOtp(@RequestParam String phone, @RequestParam String code) {
        try {
            // Call service to validate OTP
            return userService.validateOtp(phone, code);
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            return "Client Error: " + e.getResponseBodyAsString();
        } catch (Exception e) {
            // Handle generic exceptions
            return "Internal Server Error: " + e.getMessage();
        }
    }

    @PostMapping("/profile/init")
    public String initProfile(@RequestBody User user, @RequestHeader("Authorization") String tokenHeader) {
        try {
            // Extract token from "Bearer " prefix
            String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
            String phone = jwtUtil.extractPhone(token);

            if (phone == null || !jwtUtil.validateToken(token, phone)) {
                return "Access denied. Invalid or expired token";
            }

            user.setPhone(phone);
            userService.initProfile(user);
            return "User profile created";
        } catch (JwtException e) {
            return "Access denied. Invalid token";
        } catch (Exception e) {
            return "Internal Server Error";
        }
    }

    @GetMapping("/creditScore")
    public Object getCreditScore(@RequestHeader("Authorization") String tokenHeader) {
        try {
            String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
            String phone = jwtUtil.extractPhone(token);

            if (phone == null || !jwtUtil.validateToken(token, phone)) {
                return "Access denied. Invalid or expired token";
            }

            return userService.getCreditScore(phone);
        } catch (Exception e) {
            e.printStackTrace();
            return "An unexpected error occurred";
        }
    }

    @GetMapping("profile/exists")
    public Object checkProfileExistance(@RequestHeader("Authorization") String tokenHeader) {
        try {
            String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
            String phone = jwtUtil.extractPhone(token);

            if (phone == null || !jwtUtil.validateToken(token, phone)) {
                return "Access denied. Invalid or expired token";
            }

            return userService.getProfileCompletion(phone);
        } catch (Exception e) {
            e.printStackTrace();
            return "An unexpected error occurred";
        }
    }

    @GetMapping()
    public Object getUserDetails(@RequestHeader("Authorization") String tokenHeader) {
        try {
            String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
            String phone = jwtUtil.extractPhone(token);

            if (phone == null || !jwtUtil.validateToken(token, phone)) {
                return "Access denied. Invalid or expired token";
            }

            return userService.getUserMaskedDetails(phone);
        } catch (Exception e) {
            e.printStackTrace();
            return "An unexpected error occurred";
        }
    }
}