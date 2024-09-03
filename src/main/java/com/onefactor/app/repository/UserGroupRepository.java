package com.onefactor.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.onefactor.app.entity.UserGroup;


public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {}
