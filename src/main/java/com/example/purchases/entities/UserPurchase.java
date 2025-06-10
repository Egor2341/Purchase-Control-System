package com.example.purchases.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_purchases")
public class UserPurchase {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_purchase", nullable=false)
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name="id_user", nullable=false)
    private User user;

    @Column(nullable=false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable=false, precision=19, scale=2)
    private BigDecimal total;

    @Column
    private boolean paid = false;
}
