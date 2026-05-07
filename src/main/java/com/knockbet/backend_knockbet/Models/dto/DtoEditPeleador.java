package com.knockbet.backend_knockbet.Models.dto;

import java.util.UUID;

public record DtoEditPeleador(
        UUID id,
        String nombre,
        String apodo,
        Integer peso,
        Double altura,
        Double alcance,
        Integer edad
) {
}
