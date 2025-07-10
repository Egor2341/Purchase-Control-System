package com.example.purchases.controllers;

import com.example.purchases.dto.PurchaseDTO;
import com.example.purchases.dto.mapper.PurchaseMapper;
import com.example.purchases.entities.Purchase;
import com.example.purchases.exceptions.AlreadyExistException;
import com.example.purchases.exceptions.DoesNotExistException;
import com.example.purchases.responses.Response;
import com.example.purchases.security.AuthUserDetails;
import com.example.purchases.servicies.PurchaseService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases/")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;

    public PurchaseController (PurchaseService purchaseService,
                               PurchaseMapper purchaseMapper) {
        this.purchaseService = purchaseService;
        this.purchaseMapper = purchaseMapper;
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

    @GetMapping
    public ResponseEntity<List<PurchaseDTO>> getPurchases(@AuthenticationPrincipal AuthUserDetails user) {
        return new ResponseEntity<>(purchaseService.getPurchases(user.getUsername())
                .stream().map(purchaseMapper::toDTO).toList(),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Response> deletePurchase(@AuthenticationPrincipal AuthUserDetails user,
                                                   Long id) {
        try {
            purchaseService.delete(user.getUsername(), id);
        } catch (DoesNotExistException e) {
            return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
