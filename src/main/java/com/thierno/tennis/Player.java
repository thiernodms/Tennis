package com.thierno.tennis;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record Player(
        @NotBlank(message = "firstName is mandatory") String firstName,
        @NotBlank(message = "lastName is mandatory") String lastName,
        @NotNull(message = "birthDate is mandatory") @PastOrPresent(message = "birthDate must be past or present") LocalDate birthDate,
        @Valid Rank rank) {
}
