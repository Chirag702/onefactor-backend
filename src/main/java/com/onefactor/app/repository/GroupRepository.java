package com.onefactor.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.onefactor.app.entity.Groups;
import com.onefactor.app.entity.User;

public interface GroupRepository extends JpaRepository<Groups, Long> {

	List<Groups> findAllByCreatedBy(User user);}
