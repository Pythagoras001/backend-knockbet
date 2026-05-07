package com.knockbet.backend_knockbet.Models.EstrucApuesta;

import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Pelea;
import com.knockbet.backend_knockbet.Models.EstrucPredictSystem.PrediccionResultadoPelea;
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
@Table(name = "ApuestasDisponibles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Apuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "pelea")
    private Pelea pelea;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cuotaPeleadorA")
    private Cuota cuotaPeleadorA;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cuotaPeleadorB")
    private Cuota cuotaPeleadorB;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cuotaEmpate")
    private Cuota cuotaEmpate;

    @Embedded
    @Column(name = "prediccionCalculada")
    private PrediccionResultadoPelea prediccionResultadoPelea;

    @Column(name = "estadoActivo")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EstadoApuesta activo = EstadoApuesta.ABIERTA;

    @Column(name = "fechaDePublicacion")
    @Builder.Default
    private LocalDateTime fechaDePublicacion = LocalDateTime.now();

}
