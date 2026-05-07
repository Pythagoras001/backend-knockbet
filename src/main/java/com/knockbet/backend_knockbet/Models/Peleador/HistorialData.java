package com.knockbet.backend_knockbet.Models.Peleador;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Entity
@Table(name = "HistorialDatas")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "victorias")
    private int victorias;

    @Column(name = "derrotas")
    private int derrotas;

    @Column(name = "empates")
    private int empates;

    @Column(name = "totalPeleas")
    private int totalPeleas;

    @Column(name = "rachaActual")
    private int rachaActual;

    @Column(name = "rachaHistorica")
    private int rachaHistorica;

    public void actulizarEstadisticaVictoria() throws Exception{
        try {
            victorias ++;
            totalPeleas ++;
            rachaActual ++;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public void actulizarEstadisticaDerrota() throws Exception{
        try {
            derrotas ++;
            totalPeleas ++;
            rachaHistorica = Math.max(rachaHistorica, rachaActual);
            rachaActual = 0;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public void actulizarEstadisticaEmpate() throws Exception{
        try {
            empates ++;
            totalPeleas ++;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

}
