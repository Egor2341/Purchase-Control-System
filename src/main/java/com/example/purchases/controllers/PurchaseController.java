package com.example.purchases.controllers;

import com.example.purchases.entities.Purchase;
import com.example.purchases.responses.Response;
import com.example.purchases.security.AuthUserDetails;
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

    @PostMapping
    public ResponseEntity<Response> addPurchase(@RequestBody @Valid Purchase purchase,
                                                @AuthenticationPrincipal AuthUserDetails user) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
