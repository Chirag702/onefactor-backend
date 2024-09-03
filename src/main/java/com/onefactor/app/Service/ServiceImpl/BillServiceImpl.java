package com.onefactor.app.Service.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.onefactor.app.Entity.Bill;
import com.onefactor.app.Entity.BillShare;
import com.onefactor.app.Entity.Group;
import com.onefactor.app.Repository.BillRepository;
import com.onefactor.app.Repository.BillShareRepository;
import com.onefactor.app.Repository.GroupRepository;
import com.onefactor.app.Repository.UserRepository;
import com.onefactor.app.Service.BillService;
import com.onefactor.app.Utilities.ResourceNotFoundException;

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
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        bill.setGroup(group);
        Bill savedBill = billRepository.save(bill);

        for (Map.Entry<Long, Double> entry : userShares.entrySet()) {
            com.onefactor.app.Entity.User user = userRepository.findById(entry.getKey())
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
        return billRepository.findByGroupId(groupId);
    }

    @Override
    public Bill getBillById(Long id) {
        return billRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
    }
}
