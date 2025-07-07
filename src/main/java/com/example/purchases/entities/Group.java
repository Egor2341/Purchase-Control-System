package com.example.purchases.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name="groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=4, max=64)
    @Column(length = 64, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="author", nullable = false)
    private User author;

    @ManyToMany(mappedBy = "userGroups")
    Set<User> users;
}
