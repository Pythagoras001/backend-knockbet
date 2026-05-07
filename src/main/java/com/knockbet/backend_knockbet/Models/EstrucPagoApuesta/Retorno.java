package com.knockbet.backend_knockbet.Models.EstrucPagoApuesta;

import com.knockbet.backend_knockbet.Models.EstrucApuesta.ResultadoCuota;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.UserApuesta;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "retornos")
@NoArgsConstructor
@Data
public class Retorno {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "apuestaRelacionada")
    private UserApuesta apuestaInscrita;

    @Column(name = "estadoRetorno")
    @Enumerated(EnumType.STRING)
    private EstadoRetorno estadoRetorno = EstadoRetorno.PENDIENTE;

    public Retorno(UserApuesta userApuesta) throws Exception{
        try {
            if (userApuesta.getGanadorEsperado().getResultadoCuota() != ResultadoCuota.GANADA) throw new Exception("No se puede retornar a una apuesta no ganada");
            this.apuestaInscrita = userApuesta;
        }catch (Exception e){
            throw new Exception(e);
        }
    }


}
