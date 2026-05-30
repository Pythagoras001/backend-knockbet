package com.knockbet.backend_knockbet.Models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record DtoEditPeleador(
        @NotNull(message = "El id del peleador no puede ser nulo")
        UUID id,

        @NotBlank(message = "El nombre no puede estar vacio")
        @Size(max = 120, message = "El nombre no puede superar 120 caracteres")
        String nombre,

        @NotBlank(message = "El apodo no puede estar vacio")
        @Size(max = 120, message = "El apodo no puede superar 120 caracteres")
        String apodo,

        @NotNull(message = "El peso no puede ser nulo")
        @Positive(message = "El peso debe ser un numero positivo")
        Integer peso,

        @NotNull(message = "La altura no puede ser nula")
        @Positive(message = "La altura debe ser un numero positivo")
        Double altura,

        @NotNull(message = "El alcance no puede ser nulo")
        @Positive(message = "El alcance debe ser un numero positivo")
        Double alcance,

        @NotNull(message = "La edad no puede ser nula")
        @Positive(message = "La edad debe ser un numero positivo")
        Integer edad
) {
}
