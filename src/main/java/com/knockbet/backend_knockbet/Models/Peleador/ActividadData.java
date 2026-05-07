package com.knockbet.backend_knockbet.Models.Peleador;

import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Resultado;
import com.knockbet.backend_knockbet.Reglas.MetricasDeScore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "ActividadDatas")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActividadData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "ultimaPelea")
    private LocalDate ultimaPelea;

    @Column(name = "duracionPromedioEnPelea")
    private int duracionPromedioEnPelea;

    public void actulizarEstadistica(Resultado resultado) throws Exception {
        try {
            LocalDateTime inicio = resultado.getPelea().getFechaPelea();
            LocalDateTime fin = resultado.getHoraFinalizacion();

            ultimaPelea = inicio.toLocalDate();

            long duracionMin = Duration.between(inicio, fin).toMinutes();

            duracionPromedioEnPelea = Math.toIntExact(
                    (duracionPromedioEnPelea <= 0)
                            ? duracionMin
                            : (duracionPromedioEnPelea + duracionMin) / 2
            );

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public double calcularMesesDeInactividad() throws Exception{
        try {
            if (ultimaPelea == null) {
                return MetricasDeScore.ESCALA_INACTIVIDAD_MESES * Math.log(2);
            }

            long dias = ChronoUnit.DAYS.between(ultimaPelea, LocalDate.now());
            return dias / 30.0;
        }catch (Exception e){
            throw new Exception(e);
        }
    }


}
