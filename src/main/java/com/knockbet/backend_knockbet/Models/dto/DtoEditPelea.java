package com.knockbet.backend_knockbet.Models.dto;
import java.time.LocalDateTime;
import java.util.UUID;

public record DtoEditPelea(
    UUID idPelea,
    String nombrePelea,
    LocalDateTime horaIncio,
    String direccion,
    String descripcion
) {
}
