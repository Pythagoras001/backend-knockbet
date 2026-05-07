package com.knockbet.backend_knockbet.Models.EstrucApuesta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadisticaCuota {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "gananciaTotalCasa")
    private double gananciaTotalCasa = 0;

    @Column(name = "pagoTotalUser")
    private double pagoTotalPorVictoria = 0;

    @Column(name = "cantidadDeApeustasAsociadas")
    private int cantidadUserApuestasAsociadas = 0;

    public void actulizarEstadisticaCuota(UserApuesta userApuesta) throws Exception{
        try {
            pagoTotalPorVictoria += userApuesta.getTotalGananciaPosible();
            gananciaTotalCasa += userApuesta.getValorApostado();
            cantidadUserApuestasAsociadas ++;

        }catch (Exception e){
            throw new Exception(e);
        }
    }

}
