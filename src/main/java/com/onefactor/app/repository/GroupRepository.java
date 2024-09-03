package com.onefactor.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.onefactor.app.entity.Groups;

public interface GroupRepository extends JpaRepository<Groups, Long> {}
