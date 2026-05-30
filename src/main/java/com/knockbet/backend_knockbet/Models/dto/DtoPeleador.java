package com.knockbet.backend_knockbet.Models.dto;

import com.knockbet.backend_knockbet.Models.Peleador.Genero;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record DtoPeleador(

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 120, message = "El nombre no puede superar 120 caracteres")
    String nombre,

    @NotBlank(message = "El apodo no puede estar vacio")
    @Size(max = 120, message = "El apodo no puede superar 120 caracteres")
    String apodo,

    @NotNull(message = "El genero no puede ser nulo")
    Genero genero,

    @NotNull(message = "El peso no puede ser nulo")
    @Positive(message = "El peso debe ser un numero positivo")
    Integer peso,

    @NotNull(message = "La altura no puede ser nula")
    @Positive(message = "La altura debe ser un numero positivo")
    Double altura,

    @NotNull(message = "El alcance no puede ser nulo")
    @Positive(message = "El alcance debe ser un numero positivo")
    Double alcance,

    @NotNull(message = "La edad no puede ser nula")
    @Positive(message = "La edad debe ser un numero positivo")
    Integer edad,

    boolean esNuevo,

    @PastOrPresent(message = "La ultima pelea no puede ser futura")
    LocalDate ultimaPelea,

    @PositiveOrZero(message = "La duracion promedio en pelea debe ser 0 o positivo")
    Integer duracionPromedioEnPelea,

    @PositiveOrZero(message = "Las victorias deben ser 0 o positivo")
    Integer victorias,

    @PositiveOrZero(message = "Las derrotas deben ser 0 o positivo")
    Integer derrotas,

    @PositiveOrZero(message = "Los empates deben ser 0 o positivo")
    Integer empates,

    @PositiveOrZero(message = "La racha actual debe ser 0 o positivo")
    Integer rachaActual,

    @PositiveOrZero(message = "La racha historica debe ser 0 o positivo")
    Integer rachaHistorica
) {

    @AssertTrue(message = "Si no eres nuevo, debes enviar: ultimaPelea, duracionPromedioEnPelea, victorias, derrotas, empates, rachaActual y rachaHistorica")
    public boolean isDatosExperienciaValidos() {
        if (esNuevo) return true;
        return ultimaPelea != null
                && duracionPromedioEnPelea != null
                && victorias != null
                && derrotas != null
                && empates != null
                && rachaActual != null
                && rachaHistorica != null;
    }
}
