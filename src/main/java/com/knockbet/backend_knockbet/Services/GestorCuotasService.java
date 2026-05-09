package com.knockbet.backend_knockbet.Services;

import com.knockbet.backend_knockbet.Events.NuevaUserApuesta;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.Apuesta;
import com.knockbet.backend_knockbet.Models.EstrucPredictSystem.PrediccionResultadoPelea;
import com.knockbet.backend_knockbet.Models.EstrucPredictSystem.TasaRecomendada;
import com.knockbet.backend_knockbet.Reglas.MetricasDeNegocio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GestorCuotasService {

    private final PresupuestoService presupuestoService;

    public TasaRecomendada calcularTasaDeRendimientoInicial(PrediccionResultadoPelea prediccionResultadoPelea) throws Exception{
        try {

            double tasaRecomentadaA = calcularCuota(prediccionResultadoPelea.probablidadVictoriaA());
            double tasaRecomentadaB = calcularCuota(prediccionResultadoPelea.probabilidadVictoriaB());
            double tasaRecomentadaEmpate = calcularCuota(prediccionResultadoPelea.probabilidadEmpate());

            tasaRecomentadaEmpate = Math.min(tasaRecomentadaEmpate, 3.0);

            tasaRecomentadaA = Math.round(tasaRecomentadaA * 1000.0) / 1000.0;
            tasaRecomentadaB = Math.round(tasaRecomentadaB * 1000.0) / 1000.0;
            tasaRecomentadaEmpate = Math.round(tasaRecomentadaEmpate * 1000.0) / 1000.0;

            return new TasaRecomendada(tasaRecomentadaA, tasaRecomentadaB, tasaRecomentadaEmpate);

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @EventListener
    @Transactional
    public void recalcularCuotasRendimiento(NuevaUserApuesta event) throws Exception {
        Apuesta apuesta = event.userApuesta().getApuesta();
        PrediccionResultadoPelea pred = apuesta.getPrediccionResultadoPelea();

        double liabilityA = apuesta.getCuotaPeleadorA().getEstadisticaAsociada().getPagoTotalPorVictoria();
        double liabilityB = apuesta.getCuotaPeleadorB().getEstadisticaAsociada().getPagoTotalPorVictoria();
        double liabilityEmpate = apuesta.getCuotaEmpate().getEstadisticaAsociada().getPagoTotalPorVictoria();
        int bankroll = presupuestoService.verPresupuestoTotalActual();

        // 1. Probabilidades base
        double probA = pred.probablidadVictoriaA();
        double probB = pred.probabilidadVictoriaB();
        double probE = pred.probabilidadEmpate();

        // 2. Exposición
        double expA = calcularExposicion(liabilityA, bankroll);
        double expB = calcularExposicion(liabilityB, bankroll);
        double expE = calcularExposicion(liabilityEmpate, bankroll);

        // 3. Ajuste no lineal (controlado)
        double ajusteA = calcularAjusteRiesgo(expA);
        double ajusteB = calcularAjusteRiesgo(expB);
        double ajusteE = calcularAjusteRiesgo(expE);

        double probAdjA = probA * ajusteA;
        double probAdjB = probB * ajusteB;
        double probAdjE = probE * ajusteE;

        // 4. Normalización
        double suma = probAdjA + probAdjB + probAdjE;

        probAdjA /= suma;
        probAdjB /= suma;
        probAdjE /= suma;

        // 5. Cuotas
        double cuotaA = calcularCuota(probAdjA);
        double cuotaB = calcularCuota(probAdjB);
        double cuotaE = calcularCuota(probAdjE);

        // 6. Control de cuotas extremas (especial empate)
        cuotaA = Math.min(cuotaA, MetricasDeNegocio.MAX_CUOTA_PELEADOR_A);
        cuotaB = Math.min(cuotaB, MetricasDeNegocio.MAX_CUOTA_PELEADOR_B);
        cuotaE = Math.min(cuotaE, MetricasDeNegocio.MAX_CUOTA_EMPATE);

        // 7. Redondeo
        cuotaA = Math.round(cuotaA * 1000.0) / 1000.0;
        cuotaB = Math.round(cuotaB * 1000.0) / 1000.0;
        cuotaE = Math.round(cuotaE * 1000.0) / 1000.0;

        apuesta.getCuotaPeleadorA().setCuotaGananciaActual(cuotaA);
        apuesta.getCuotaPeleadorB().setCuotaGananciaActual(cuotaB);
        apuesta.getCuotaEmpate().setCuotaGananciaActual(cuotaE);

    }

    private double calcularExposicion(double liability, double bankroll) {
        if (bankroll <= 0) {
            throw new IllegalArgumentException("El bankroll debe ser mayor que 0");
        }
        return liability / bankroll;
    }

    private double calcularAjusteRiesgo(double exposicion) {
        double ajuste = Math.pow(exposicion, MetricasDeNegocio.EXPONENTE_RIESGO)
                * MetricasDeNegocio.FACTOR_RIESGO;

        return 1 + Math.min(ajuste, MetricasDeNegocio.MAX_AJUSTE_RIESGO);
    }

    private double calcularCuota(double probabilidad) {
        if (probabilidad <= 0) {
            throw new IllegalArgumentException("La probabilidad debe ser mayor que 0");
        }

        double cuota = 1 / (MetricasDeNegocio.MARGEN_DE_GANANCIA * probabilidad);

        return cuota;
    }

}
