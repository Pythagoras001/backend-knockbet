package com.knockbet.backend_knockbet.Models.EstrucEncuentro;

import jakarta.persistence.Embeddable;

@Embeddable
public record Ubicacion(
        String dirreccion,
        String Descripcion
) {
}
