package com.onefactor.app.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.onefactor.app.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

	List<Bill> findByGroupsId(Long groupId);

 }
