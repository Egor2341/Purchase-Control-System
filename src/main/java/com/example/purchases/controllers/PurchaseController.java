package com.example.purchases.controllers;

import com.example.purchases.entities.Purchase;
import com.example.purchases.exceptions.AlreadyExistException;
import com.example.purchases.responses.Response;
import com.example.purchases.security.AuthUserDetails;
import com.example.purchases.servicies.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases/")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController (PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<Response> addPurchase(@RequestBody @Valid Purchase purchase,
                                                @AuthenticationPrincipal AuthUserDetails user) {
        try {
            purchaseService.save(user.getUsername(), purchase);
        } catch (AlreadyExistException e) {
            return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
