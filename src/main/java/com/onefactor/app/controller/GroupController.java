package com.onefactor.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onefactor.app.entity.Groups;
import com.onefactor.app.entity.User;
import com.onefactor.app.service.GroupService;
import com.onefactor.app.service.UserService;
import com.onefactor.app.utilities.JWT.JWTUtil;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@PostMapping
	public ResponseEntity<?> createGroup(@RequestBody Groups groups,
			@RequestHeader("Authorization") String tokenHeader) {
		try {
			// Extract token from "Bearer " prefix
			String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
			String phone = jwtUtil.extractPhone(token);

			if (phone == null || !jwtUtil.validateToken(token, phone)) {
				return new ResponseEntity<>("Token invalid or failed to fetch phone from token",
						HttpStatus.UNAUTHORIZED);
			}

			Groups createdGroup = groupService.createGroup(groups, phone);
			return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred while creating the groups",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<List<Groups>> getAllGroups() {
		List<Groups> groups = groupService.getAllGroups();
		return new ResponseEntity<>(groups, HttpStatus.OK);
	}

	@GetMapping("/m")
	public ResponseEntity<?> getGroupById(@RequestHeader("Authorization") String tokenHeader) {

		try {
			// Extract token from "Bearer " prefix
			String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
			String phone = jwtUtil.extractPhone(token);

			if (phone == null || !jwtUtil.validateToken(token, phone)) {
				return new ResponseEntity<>("Token invalid or failed to fetch phone from token",
						HttpStatus.UNAUTHORIZED);
			}

			List<Groups> groups = groupService.getGroupByUser(phone);
			return new ResponseEntity<>(groups, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred while creating the groups",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{groupId}/users/{userId}")
	public ResponseEntity<Void> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
		groupService.addUserToGroup(groupId, userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
