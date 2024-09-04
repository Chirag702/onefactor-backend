package com.onefactor.app.service.serviceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onefactor.app.entity.Groups;
import com.onefactor.app.entity.User;
import com.onefactor.app.entity.UserGroup;
import com.onefactor.app.repository.GroupRepository;
import com.onefactor.app.repository.UserGroupRepository;
import com.onefactor.app.repository.UserRepository;
import com.onefactor.app.service.GroupService;
import com.onefactor.app.utilities.ResourceNotFoundException;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserGroupRepository userGroupRepository;

	@Override
	public List<Groups> getAllGroups() {
		return groupRepository.findAll();
	}

	@Override
	public Groups getGroupById(Long id) {
		return groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
	}

	@Override
	public void addUserToGroup(Long groupId, Long userId) {
		Groups groups = getGroupById(groupId);
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		UserGroup userGroup = new UserGroup();
		userGroup.setGroups(groups);
		userGroup.setUser(user);
		userGroupRepository.save(userGroup);
	}

	@Override
	public Groups createGroup(Groups groups, String phone) {
		User user = userRepository.findByPhone(phone);
		groups.setCreatedBy(user);
		return groupRepository.save(groups);

	}

	@Override
	public List<Groups> getGroupByUser(String phone) {
		User user=userRepository.findByPhone(phone);
		List<Groups> groups=groupRepository.findAllByCreatedBy(user);
		return groups;
	}
}
