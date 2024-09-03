package com.onefactor.app.Service;

import java.util.List;
import java.util.Map;

import com.onefactor.app.Entity.Bill;

public interface BillService {
    Bill createBill(Long groupId, Bill bill, Map<Long, Double> userShares);
    List<Bill> getAllBillsForGroup(Long groupId);
    Bill getBillById(Long id);
}
