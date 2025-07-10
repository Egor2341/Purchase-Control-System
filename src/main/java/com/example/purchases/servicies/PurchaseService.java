package com.example.purchases.servicies;

import com.example.purchases.entities.Group;
import com.example.purchases.entities.Purchase;
import com.example.purchases.entities.User;
import com.example.purchases.exceptions.AlreadyExistException;
import com.example.purchases.repositories.PurchaseRepository;
import com.example.purchases.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;

    public PurchaseService (PurchaseRepository purchaseRepository,
                            UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
    }

    public void save(String username, Purchase purchase) {
        User user = userRepository.findByUsername(username).get();
        if (user.getPurchases().stream().anyMatch(p -> p.getDate() == purchase.getDate())) {
            throw new AlreadyExistException("Purchase with this date already exist");
        }
        purchaseRepository.save(purchase);
    }
}
