package com.example.purchases.servicies;

import com.example.purchases.entities.Group;
import com.example.purchases.entities.Purchase;
import com.example.purchases.repositories.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseService (PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public void save(Purchase purchase) {

    }
}
