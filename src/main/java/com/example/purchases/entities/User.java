package com.example.purchases.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min=3, max=64)
    @Column(length = 64, nullable = false, unique = true)
    private String username;

    @Size(min=8, max=64)
    @Column(length = 128, nullable = false)
    private String password;

    @Email
    @Column(length = 128, nullable = false, unique = true)
    private String email;

    @ManyToMany
    private Set<Group> userGroups;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Purchase> purchases;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private Set<UserPurchase> userPurchases;
}
