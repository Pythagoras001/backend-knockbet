package com.knockbet.backend_knockbet.Models.EstrucPresupuesto;

import com.knockbet.backend_knockbet.Models.EstrucApuesta.UserApuesta;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Presupuesto {

    @Id
    private Long id;

    private int presupuestoInicial;
    private int presupuestoTotal;
    private int gananciasTotales;
    private int pagosTotales;

    public Presupuesto() {
        this.id = 1L;
        this.presupuestoInicial = 0;
        this.presupuestoTotal = 0;
        this.gananciasTotales = 0;
        this.pagosTotales = 0;
    }

    public void incrementarPresupuesto(int monto) throws Exception{
        try {
            this.presupuestoInicial += monto;
            this.presupuestoTotal += monto;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public void actulizarPresupuestoPorApuesta(UserApuesta userApuesta) throws Exception{
        try {
            switch (userApuesta.getGanadorEsperado().getResultadoCuota()) {
                case ESPERA:
                    this.presupuestoTotal += userApuesta.getValorApostado();
                    break;

                case GANADA:
                    this.pagosTotales += userApuesta.getTotalGananciaPosible();
                    this.presupuestoTotal -= userApuesta.getTotalGananciaPosible();
                    break;

                default:
                    this.gananciasTotales += userApuesta.getValorApostado();
                    break;
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
