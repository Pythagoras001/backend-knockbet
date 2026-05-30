package com.knockbet.backend_knockbet.Models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DtoDeposito(
        @NotNull(message = "El monto no puede ser nulo")
        @Positive(message = "El monto debe ser un numero positivo")
        Integer monto
) {
}
