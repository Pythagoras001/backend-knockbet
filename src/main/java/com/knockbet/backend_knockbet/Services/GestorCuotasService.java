package com.knockbet.backend_knockbet.Services;

import com.knockbet.backend_knockbet.Models.EstrucPredictSystem.PrediccionResultadoPelea;
import com.knockbet.backend_knockbet.Models.EstrucPredictSystem.TasaRecomendada;
import com.knockbet.backend_knockbet.Reglas.MetricasDeNegocio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GestorCuotasService {

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

    private double calcularCuota(double probabilidad) {
        if (probabilidad <= 0) {
            throw new IllegalArgumentException("La probabilidad debe ser mayor que 0");
        }

        double cuota = 1 / (MetricasDeNegocio.MARGEN_DE_GANANCIA * probabilidad);

        return cuota;
    }
}
