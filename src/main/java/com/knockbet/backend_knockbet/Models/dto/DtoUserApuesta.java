package com.knockbet.backend_knockbet.Models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record DtoUserApuesta(
    @NotBlank(message = "El nombre del apostador no puede estar vacio")
    @Size(max = 120, message = "El nombre del apostador no puede superar 120 caracteres")
    String nombreApostador,

    @NotBlank(message = "La cedula no puede estar vacia")
    @Size(max = 20, message = "La cedula no puede superar 20 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "La cedula solo puede contener numeros")
    String cedula,

    @NotBlank(message = "El celular no puede estar vacio")
    @Size(max = 20, message = "El celular no puede superar 20 caracteres")
    @Pattern(regexp = "^[0-9+]+$", message = "El celular solo puede contener numeros y el signo +")
    String celular,

    @NotBlank(message = "El correo no puede estar vacio")
    @Email(message = "El correo no tiene un formato valido")
    @Size(max = 200, message = "El correo no puede superar 200 caracteres")
    String correo,

    @NotNull(message = "El id de la apuesta no puede ser nulo")
    UUID apuesta,

    @NotNull(message = "El id del ganador esperado no puede ser nulo")
    UUID ganadorEsperado,

    @Positive(message = "El valor apostado debe ser un numero positivo")
    long valorApostado) {
}
