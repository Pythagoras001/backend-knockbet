package com.knockbet.backend_knockbet.Services;

import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Pelea;
import com.knockbet.backend_knockbet.Models.EstrucPredictSystem.PrediccionResultadoPelea;
import com.knockbet.backend_knockbet.Models.Peleador.Peleador;
import com.knockbet.backend_knockbet.Reglas.MetricasDeScore;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    private double calcularScorePeleador(Peleador peleador) throws Exception {
        try {
            // --- HISTORIAL ---
            double victorias = peleador.getHistorialData().getVictorias();
            double derrotas = peleador.getHistorialData().getDerrotas();
            double total = peleador.getHistorialData().getTotalPeleas();

            double normWinRate = (victorias + MetricasDeScore.K_SMOOTHING * MetricasDeScore.PRIORS_WINRATE) / (total + MetricasDeScore.K_SMOOTHING);
            double normExperiencia = Math.min(1, total / MetricasDeScore.MAX_PELEAS_EXPERIENCIA);
            double normDerrotas = Math.min(1, derrotas / MetricasDeScore.MAX_DERROTAS);
            double normRacha = Math.min(1, peleador.getHistorialData().getRachaActual() / MetricasDeScore.MAX_RACHA);
            double normRachaHistorica = Math.min(1,peleador.getHistorialData().getRachaHistorica() / MetricasDeScore.MAX_RACHA_HISTORICA);

            // --- FÍSICO ---
            double edad = peleador.getFisicoData().getEdad();
            double normEdad = Math.max(0, 1 - Math.abs(edad - MetricasDeScore.EDAD_OPTIMA) / MetricasDeScore.RANGO_EDAD);

            // --- ACTIVIDAD ---
            double mesesInactivo = peleador.getActividadData().calcularMesesDeInactividad();
            double normActividad = Math.exp(-mesesInactivo / MetricasDeScore.ESCALA_INACTIVIDAD_MESES);

            // --- TIEMPO EN PELEA ---
            double duracion = peleador.getActividadData().getDuracionPromedioEnPelea();
            double normDuracion = Math.max(0, 1 - duracion / MetricasDeScore.DURACION_REFERENCIA_PELEA);

            // --- SCORE FINAL (balanceado) ---
            double score =
                    (normWinRate * MetricasDeScore.PESO_WIN_RATE) +
                            (normExperiencia * MetricasDeScore.PESO_EXPERIENCIA) +
                            (normRacha * MetricasDeScore.PESO_RACHA) +
                            (normRachaHistorica * MetricasDeScore.PESO_RACHA_HISTORICA) +
                            (normEdad * MetricasDeScore.PESO_EDAD) +
                            (normActividad * MetricasDeScore.PESO_ACTIVIDAD) +
                            (normDuracion * MetricasDeScore.PESO_DURACION)
                            - (normDerrotas * MetricasDeScore.PESO_DERROTAS);

            return score;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private double calcularVentajaFisica(Peleador peleadorA, Peleador peleadorB) {
        double alturaDiff = peleadorA.getFisicoData().getAltura() - peleadorB.getFisicoData().getAltura();
        double alcanceDiff = peleadorA.getFisicoData().getAlcance() - peleadorB.getFisicoData().getAlcance();
        double pesoDiff = peleadorA.getFisicoData().getPeso() - peleadorB.getFisicoData().getPeso();

        double ventajaAltura = Math.max(-1, Math.min(1, alturaDiff / MetricasDeScore.ESCALA_ALTURA));
        double ventajaAlcance = Math.max(-1, Math.min(1, alcanceDiff / MetricasDeScore.ESCALA_ALCANCE));
        double ventajaPeso = Math.max(-1, Math.min(1, pesoDiff / MetricasDeScore.ESCALA_PESO));

        return (ventajaAltura * MetricasDeScore.PESO_ALTURA) +
                (ventajaAlcance * MetricasDeScore.PESO_ALCANCE) +
                (ventajaPeso * MetricasDeScore.PESO_PESO);
    }

    public PrediccionResultadoPelea calcularProbabilidadDeVictoria(Pelea pelea) throws Exception {
        try {
            Peleador peleadorA = pelea.getPeleadorA();
            Peleador peleadorB = pelea.getPeleadorB();

            // --- SCORE BASE ---
            double scoreA = calcularScorePeleador(peleadorA);
            double scoreB = calcularScorePeleador(peleadorB);

            // --- VENTAJAS RELATIVAS ---
            double ventajaFisicaA = calcularVentajaFisica(peleadorA, peleadorB);
            double ventajaFisicaB = calcularVentajaFisica(peleadorB, peleadorA);

            double eloA = scoreA + ventajaFisicaA;
            double eloB = scoreB + ventajaFisicaB;

            // --- ESCALA LOGÍSTICA (CLAVE) ---
            double probABase = 1 / (1 + Math.exp((eloB - eloA) * MetricasDeScore.ESCALA_LOGISTICA));

            // --- EMPATE ---
            double diferencia = Math.abs(eloA - eloB);

            double baseEmpate = MetricasDeScore.PROB_BASE_EMPATE;
            double probEmpate = baseEmpate * Math.exp(-diferencia * MetricasDeScore.SENSIBILIDAD_EMPATE);

            // --- AJUSTE FINAL ---
            double probA = probABase * (1 - probEmpate);
            double probB = (1 - probABase) * (1 - probEmpate);

            // --- NORMALIZACIÓN POR SEGURIDAD ---
            double suma = probA + probB + probEmpate;
            probA /= suma;
            probB /= suma;
            probEmpate /= suma;

            // --- REDONDEO ---
            probA = Math.round(probA * 1000.0) / 1000.0;
            probB = Math.round(probB * 1000.0) / 1000.0;
            probEmpate = Math.round(probEmpate * 1000.0) / 1000.0;

            return new PrediccionResultadoPelea(probA, probB, probEmpate);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}