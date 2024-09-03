package com.onefactor.app.Service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onefactor.app.Entity.Group;
import com.onefactor.app.Entity.User;
import com.onefactor.app.Entity.UserGroup;
import com.onefactor.app.Repository.GroupRepository;
import com.onefactor.app.Repository.UserGroupRepository;
import com.onefactor.app.Repository.UserRepository;
import com.onefactor.app.Service.GroupService;
import com.onefactor.app.Utilities.ResourceNotFoundException;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserGroupRepository userGroupRepository;

	@Override
	public List<Group> getAllGroups() {
		return groupRepository.findAll();
	}

	@Override
	public Group getGroupById(Long id) {
		return groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
	}

	@Override
	public void addUserToGroup(Long groupId, Long userId) {
		Group group = getGroupById(groupId);
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		UserGroup userGroup = new UserGroup();
		userGroup.setGroup(group);
		userGroup.setUser(user);
		userGroupRepository.save(userGroup);
	}

	@Override
	public Group createGroup(Group group, String phone) {
		User user = userRepository.findByPhone(phone);
		group.setCreatedBy(user);
		return groupRepository.save(group);

	}
}
