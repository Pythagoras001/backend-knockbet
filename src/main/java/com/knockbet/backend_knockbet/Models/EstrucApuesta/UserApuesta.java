package com.knockbet.backend_knockbet.Models.EstrucApuesta;

import com.knockbet.backend_knockbet.Models.EstrucApostador.Apostador;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "UserApuestas")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserApuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Embedded
    private Apostador apostador;

    @ManyToOne
    @JoinColumn(name = "apuesta")
    private Apuesta apuesta;

    @ManyToOne
    @JoinColumn(name = "ganadorEsperado")
    private Cuota ganadorEsperado;

    @Column(name = "rendimiento_victoria")
    private double rendimientoGananciaAsociada;

    @Column(name = "valorApostado")
    private long valorApostado;

    @Column(name = "totalGananciaPosible")
    private long totalGananciaPosible;

    @Builder
    public UserApuesta(Apostador apostador, Apuesta apuesta, Cuota ganadorEsperado, double rendimientoGananciaAsociada, long valorApostado) {
        this.apostador = apostador;
        this.apuesta = apuesta;
        this.ganadorEsperado = ganadorEsperado;
        this.rendimientoGananciaAsociada = rendimientoGananciaAsociada;
        this.valorApostado = valorApostado;

        this.totalGananciaPosible = (long) (this.valorApostado * this.rendimientoGananciaAsociada);
    }

}
