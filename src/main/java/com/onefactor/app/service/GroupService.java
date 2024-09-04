package com.onefactor.app.service;

import java.util.List;


import com.onefactor.app.entity.Groups;

public interface GroupService {
    Groups createGroup(Groups groups, String phone);
    List<Groups> getAllGroups();
    Groups getGroupById(Long id);
    void addUserToGroup(Long groupId, Long userId);
	List<Groups> getGroupByUser(String phone);
}
