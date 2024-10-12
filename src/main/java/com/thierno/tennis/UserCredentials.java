package com.thierno.tennis;

import jakarta.validation.constraints.NotBlank;

public record UserCredentials(
        @NotBlank(message = "login is mandatory") String login,
        @NotBlank(message = "password is mandatory") String password) {
}
