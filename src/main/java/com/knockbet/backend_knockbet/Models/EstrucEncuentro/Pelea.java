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
@Table(name = "Peleas")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Pelea {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "nombreEvento")
    private String tituloPelea;

    @ManyToOne
    @JoinColumn(name = "peleadorA")
    private Peleador peleadorA;

    @ManyToOne
    @JoinColumn(name = "peleadorB")
    private Peleador peleadorB;

    @Embedded
    private Ubicacion ubicacion;

    @Column(name = "fechaPelea")
    private LocalDateTime fechaPelea;

    @Column(name = "estadoPelea")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoPelea estadoPelea = EstadoPelea.PROGRAMADA;

}
