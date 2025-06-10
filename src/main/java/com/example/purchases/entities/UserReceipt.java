package com.example.purchases.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_receipts")
public class UserReceipt {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_purchase", nullable=false)
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(nullable=false, referencedColumnName="NAME")
    private Product product;

    @Column
    private int count = 1;

    @Column(nullable=false, precision=19, scale=2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(nullable=false, referencedColumnName="NAME")
    private Market market;
}
