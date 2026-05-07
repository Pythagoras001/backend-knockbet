package com.knockbet.backend_knockbet.Models.dto;

import com.knockbet.backend_knockbet.Models.Peleador.Genero;

import java.time.LocalDate;

public record DtoPeleador(
        String nombre,
        String apodo,
        Genero genero,

         Integer peso,
         Double altura,
         Double alcance,
         Integer edad,

         boolean esNuevo,

         LocalDate ultimaPelea,
         Integer duracionPromedioEnPelea,

         Integer victorias,
         Integer derrotas,
         Integer empates,
         Integer rachaActual,
         Integer rachaHistorica
) {
}
