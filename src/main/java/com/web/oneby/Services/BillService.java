package com.web.oneby.Services;

import com.web.oneby.Repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {

    private BillRepository billRepository;

    @Autowired
    public BillService (
            BillRepository billRepository
    ) {
        this.billRepository = billRepository;
    }

}

