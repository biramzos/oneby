package com.web.oneby.Services;

import com.web.oneby.Repositories.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

    private BasketRepository basketRepository;

    @Autowired
    public BasketService (
            BasketRepository basketRepository
    ) {
        this.basketRepository = basketRepository;
    }

}
