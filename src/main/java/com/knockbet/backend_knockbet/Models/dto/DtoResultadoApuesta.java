package com.knockbet.backend_knockbet.Models.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DtoResultadoApuesta(
    @NotNull(message = "El id de la pelea no puede ser nulo")
    UUID pelea,

    UUID peleadorGanador,

    @NotNull(message = "El campo empate no puede ser nulo")
    Boolean empate
) {
    @AssertTrue(message = "Si empate es false, debes enviar el id del peleadorGanador. Si empate es true, peleadorGanador debe ser nulo")
    public boolean isGanadorConsistente() {
        if (empate == null) return false;
        if (empate) return peleadorGanador == null;
        return peleadorGanador != null;
    }
}
