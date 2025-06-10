package com.example.purchases.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="markets")
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length=64, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy="market")
    private Set<Receipt> receipts;

    @OneToMany(mappedBy="market")
    private Set<UserReceipt> userReceipts;
}
