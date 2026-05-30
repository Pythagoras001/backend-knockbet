package com.knockbet.backend_knockbet.Models.dto;

import com.knockbet.backend_knockbet.Models.EstrucPagoApuesta.MetodoPago;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DtoPago(
        @NotNull(message = "El id del retorno no puede ser nulo")
        UUID idRetorno,

        @NotNull(message = "El metodo de pago no puede ser nulo")
        MetodoPago metodoPago
) {
}
