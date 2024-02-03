package com.web.oneby.books.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.oneby.books.Repositories.BillRepository;

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

