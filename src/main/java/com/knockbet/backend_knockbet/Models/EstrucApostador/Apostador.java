package com.knockbet.backend_knockbet.Models.EstrucApostador;

import jakarta.persistence.Embeddable;

@Embeddable
public record Apostador(
        String nombre,
        String cedula,
        String celular
) {
}
