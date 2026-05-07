package com.knockbet.backend_knockbet.Models.EstrucEncuentro;

import com.knockbet.backend_knockbet.Models.Peleador.Peleador;
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
@Table(name = "Resultados")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "pelea")
    private Pelea pelea;

    @ManyToOne
    @JoinColumn(name = "ganador")
    private Peleador ganador;

    @ManyToOne
    @JoinColumn(name = "perdedor")
    private Peleador perdedor;

    @Column(name = "horaFinalizacion")
    private LocalDateTime horaFinalizacion;

}
