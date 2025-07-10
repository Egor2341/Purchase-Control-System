package com.example.purchases.servicies;

import com.example.purchases.entities.Purchase;
import com.example.purchases.entities.User;
import com.example.purchases.exceptions.AlreadyExistException;
import com.example.purchases.exceptions.DoesNotExistException;
import com.example.purchases.repositories.PurchaseRepository;
import com.example.purchases.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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
        if (user.getPurchases().stream().anyMatch(p -> p.getDate().equals(purchase.getDate()))) {
            throw new AlreadyExistException("Purchase with this date already exist");
        }
        user.getPurchases().add(purchase);
        purchase.setUser(user);
        purchaseRepository.save(purchase);
    }

    public Set<Purchase> getPurchases (String username) {
        User user = userRepository.findByUsername(username).get();
        return user.getPurchases();
    }

    public void delete (String username, Long id) {
        User user = userRepository.findByUsername(username).get();
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        if (purchase.isEmpty()) {
            throw new DoesNotExistException("Purchase with this ID does not exist");
        }
        user.getPurchases().remove(purchase.get());
        purchaseRepository.delete(purchase.get());
    }
}
