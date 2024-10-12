package com.thierno.tennis;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record Rank(
        @Positive(message = "position must be postive number") int position,
        @PositiveOrZero(message = "points must be positive or 0") int points) {
}
