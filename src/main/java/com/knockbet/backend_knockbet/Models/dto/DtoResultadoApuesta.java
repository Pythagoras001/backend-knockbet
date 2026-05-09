package com.knockbet.backend_knockbet.Models.dto;

import java.util.UUID;

public record DtoResultadoApuesta(
    UUID pelea,
    UUID peleadorGanador,
    Boolean empate
) {
}
