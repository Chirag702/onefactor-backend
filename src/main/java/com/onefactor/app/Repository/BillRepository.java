package com.onefactor.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onefactor.app.Entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

	List<Bill> findByGroupId(Long groupId);
}
