package com.knockbet.backend_knockbet.Models.EstrucApuesta;

import com.knockbet.backend_knockbet.Models.Peleador.Peleador;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "Cuotas")
@NoArgsConstructor
@Data
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "peleador")
    private Peleador peleador;

    @Column(name = "cuotaDeGananciaActual")
    private double cuotaGananciaActual;

    @Column(name = "resultadoCuota")
    @Enumerated(EnumType.STRING)
    private ResultadoCuota resultadoCuota = ResultadoCuota.ESPERA;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estadisticaAsociada")
    private EstadisticaCuota estadisticaAsociada;

    public Cuota(Peleador peleador, double cuotaGananciaActual) {
        this.cuotaGananciaActual = cuotaGananciaActual;
        this.peleador = peleador;
        this.estadisticaAsociada = new EstadisticaCuota();
    }
}
