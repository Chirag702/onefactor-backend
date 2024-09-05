package com.onefactor.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.onefactor.app.entity.Bill;
import com.onefactor.app.entity.DTO.BillRequest;
import com.onefactor.app.service.BillService;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/groups/{groupId}")
    public ResponseEntity<Bill> createBill(@PathVariable Long groupId, 
                                           @RequestBody BillRequest billRequest) {
        Bill createdBill = billService.createBill(groupId, billRequest.getBill(), billRequest.getUserShares());
        return new ResponseEntity<>(createdBill, HttpStatus.CREATED);
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<Bill>> getAllBillsForGroup(@PathVariable Long groupId) {
        List<Bill> bills = billService.getAllBillsForGroup(groupId);
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        Bill bill = billService.getBillById(id);
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }
}
