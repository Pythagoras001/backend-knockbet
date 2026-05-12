package com.knockbet.backend_knockbet.Models.EstrucPagoApuesta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "facturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @JoinColumn(name = "retornoAsociado")
    @OneToOne
    private Retorno retorno;

    @Column(name = "fechaPago")
    private LocalDateTime fechaPago;

    @Column(name = "metodoPago")
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

}
