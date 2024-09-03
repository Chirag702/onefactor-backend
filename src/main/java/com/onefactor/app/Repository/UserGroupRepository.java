package com.onefactor.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onefactor.app.Entity.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {}
