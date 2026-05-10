package com.knockbet.backend_knockbet.Models.dto;

import java.util.UUID;

public record DtoUserApuesta(
    String nombreApostador,
    String cedula,
    String celular,
    String correo,
    UUID apuesta,
    UUID ganadorEsperado,
    long valorApostado) {
}
