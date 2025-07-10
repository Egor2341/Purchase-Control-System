package com.example.purchases.dto.mapper;

import com.example.purchases.dto.PurchaseDTO;
import com.example.purchases.entities.Purchase;
import org.springframework.stereotype.Service;

@Service
public class PurchaseMapper {
    public PurchaseDTO toDTO(Purchase purchase) {
        return new PurchaseDTO(purchase.getId(),
                purchase.getDate(),
                purchase.getTotal()
                );
    }
}
