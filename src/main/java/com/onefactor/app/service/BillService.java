package com.onefactor.app.service;

import java.util.List;

import java.util.Map;

import com.onefactor.app.entity.Bill;

public interface BillService {
    Bill createBill(Long groupId, Bill bill, Map<Long, Double> userShares);
    List<Bill> getAllBillsForGroup(Long groupId);
    Bill getBillById(Long id);
}
