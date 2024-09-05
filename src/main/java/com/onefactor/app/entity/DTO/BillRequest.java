package com.onefactor.app.entity.DTO;

import java.util.Map;

import com.onefactor.app.entity.Bill;

public class BillRequest {
    private Bill bill;
    private Map<Long, Double> userShares;

    // Getters and Setters
    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Map<Long, Double> getUserShares() {
        return userShares;
    }

    public void setUserShares(Map<Long, Double> userShares) {
        this.userShares = userShares;
    }
}
