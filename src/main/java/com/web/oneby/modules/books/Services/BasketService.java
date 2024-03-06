package com.web.oneby.modules.books.Services;

import com.web.oneby.modules.books.Repositories.BasketRepository;
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
