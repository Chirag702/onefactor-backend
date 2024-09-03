package com.onefactor.app.Controller;

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

import com.onefactor.app.Entity.Group;
import com.onefactor.app.Service.GroupService;
import com.onefactor.app.Service.UserService;
import com.onefactor.app.Utlities.JWT.JWTUtil;

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
    public ResponseEntity<?> createGroup(@RequestBody Group group, @RequestHeader("Authorization") String tokenHeader) {
        try {
            // Extract token from "Bearer " prefix
            String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
            String phone = jwtUtil.extractPhone(token);

            if (phone == null || !jwtUtil.validateToken(token, phone)) {
                return new ResponseEntity<>("Token invalid or failed to fetch phone from token", HttpStatus.UNAUTHORIZED);
            }

            Group createdGroup = groupService.createGroup(group, phone);
            return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while creating the group", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        Group group = groupService.getGroupById(id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PostMapping("/{groupId}/users/{userId}")
    public ResponseEntity<Void> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        groupService.addUserToGroup(groupId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
