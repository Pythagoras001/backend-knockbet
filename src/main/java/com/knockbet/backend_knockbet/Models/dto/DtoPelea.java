package com.knockbet.backend_knockbet.Models.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record DtoPelea(

    String nombrePelea,
    UUID idPeleadorA,
    UUID idPeleadorB,

    LocalDateTime horaIncio,
    String direccion,
    String descripcion,
    boolean asociarApuesta

) {
}
