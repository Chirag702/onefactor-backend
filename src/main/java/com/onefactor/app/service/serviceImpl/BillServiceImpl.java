package com.onefactor.app.service.serviceImpl;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.onefactor.app.entity.Bill;
import com.onefactor.app.entity.BillShare;
import com.onefactor.app.entity.Groups;
import com.onefactor.app.entity.User;
import com.onefactor.app.repository.BillRepository;
import com.onefactor.app.repository.BillShareRepository;
import com.onefactor.app.repository.GroupRepository;
import com.onefactor.app.repository.UserRepository;
import com.onefactor.app.service.BillService;
import com.onefactor.app.utilities.ResourceNotFoundException;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillShareRepository billShareRepository;

    @Override
    public Bill createBill(Long groupId, Bill bill, Map<Long, Double> userShares) {
        Groups groups = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        bill.setGroups(groups);
        Bill savedBill = billRepository.save(bill);

        for (Map.Entry<Long, Double> entry : userShares.entrySet()) {
            User user = userRepository.findById(entry.getKey())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            BillShare billShare = new BillShare();
            billShare.setBill(savedBill);
            billShare.setUser(user);
            billShare.setAmount(entry.getValue());

            billShareRepository.save(billShare);
        }

        return savedBill;
    }

    @Override
    public List<Bill> getAllBillsForGroup(Long groupId) {
        return billRepository.findByGroupsId(groupId);
    }

    @Override
    public Bill getBillById(Long id) {
        return billRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
    }
}
