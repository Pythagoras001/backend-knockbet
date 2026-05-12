package com.knockbet.backend_knockbet.Models.dto;

import com.knockbet.backend_knockbet.Models.EstrucPagoApuesta.MetodoPago;

import java.util.UUID;

public record DtoPago(
        UUID idRetorno,
        MetodoPago metodoPago
) {
}
