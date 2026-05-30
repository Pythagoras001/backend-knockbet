package com.knockbet.backend_knockbet.Models.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

public record DtoPelea(

    @NotBlank(message = "El nombre de la pelea no puede estar vacio")
    @Size(max = 120, message = "El nombre de la pelea no puede superar 120 caracteres")
    String nombrePelea,

    @NotNull(message = "El id del peleador A no puede ser nulo")
    UUID idPeleadorA,

    @NotNull(message = "El id del peleador B no puede ser nulo")
    UUID idPeleadorB,

    @NotNull(message = "La hora de inicio no puede ser nula")
    @FutureOrPresent(message = "La hora de inicio no puede ser pasada")
    LocalDateTime horaIncio,

    @NotBlank(message = "La direccion no puede estar vacia")
    @Size(max = 200, message = "La direccion no puede superar 200 caracteres")
    String direccion,

    @NotBlank(message = "La descripcion no puede estar vacia")
    @Size(max = 500, message = "La descripcion no puede superar 500 caracteres")
    String descripcion,
    boolean asociarApuesta

) {
}
