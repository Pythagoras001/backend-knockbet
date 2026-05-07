package com.knockbet.backend_knockbet.Models.EstrucPagoApuesta;

import java.time.LocalDateTime;
import java.util.UUID;

public class Factura {

    private UUID id;
    private Retorno retorno;
    private LocalDateTime fechaPago;
    private MetodoPago metodoPago;

}
