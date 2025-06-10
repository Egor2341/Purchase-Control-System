package com.example.purchases.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable=false, precision = 19, scale = 2)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name="id_user", nullable=false)
    private User user;

    @OneToMany(mappedBy="purchase", cascade=CascadeType.ALL)
    private Set<Receipt> receipts;

    @OneToMany(mappedBy="purchase", cascade=CascadeType.ALL)
    private Set<UserPurchase> userPurchases;

    @OneToMany(mappedBy="purchase", cascade=CascadeType.ALL)
    private Set<UserReceipt> userReceipts;
}
