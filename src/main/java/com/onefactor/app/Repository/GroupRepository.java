package com.onefactor.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onefactor.app.Entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {}
