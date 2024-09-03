package com.onefactor.app.Service;

import java.util.List;

import com.onefactor.app.Entity.Group;

public interface GroupService {
    Group createGroup(Group group, String phone);
    List<Group> getAllGroups();
    Group getGroupById(Long id);
    void addUserToGroup(Long groupId, Long userId);
}
