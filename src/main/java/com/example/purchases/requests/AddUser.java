package com.example.purchases.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddUser {

    @NotNull
    private final Long id_group;

    @NotNull
    @Size(min=3, max=64)
    private final String username;
}
