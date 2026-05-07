package com.knockbet.backend_knockbet.Services;

import com.knockbet.backend_knockbet.Models.Peleador.*;
import com.knockbet.backend_knockbet.Models.dto.DtoPeleador;
import com.knockbet.backend_knockbet.Reglas.MetricasDeScore;
import com.knockbet.backend_knockbet.Repository.PeleadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PeleadorService {

    private final PeleadorRepository peleadorRepository;

    public void registrarPeleador(DtoPeleador dtoPeleador) throws Exception{
        try {
            if (dtoPeleador.esNuevo()) registrarPeleadorNovato(dtoPeleador);
            else registrarPeleadorExperiencia(dtoPeleador);

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private void registrarPeleadorNovato(DtoPeleador dtoPeleador) throws Exception{
        try {
            CategoriaPeso categoriaPeso = CategoriaPeso.calcularTipoPeso(dtoPeleador.peso());

            FisicoData fisicoData = FisicoData.builder()
                    .peso(dtoPeleador.peso())
                    .altura(dtoPeleador.altura())
                    .alcance(dtoPeleador.alcance())
                    .edad(dtoPeleador.edad())
                    .categoriaPeso(categoriaPeso)
                    .build();

            ActividadData actividadData = ActividadData.builder()
                    .ultimaPelea(null)
                    .duracionPromedioEnPelea((int) MetricasDeScore.DURACION_REFERENCIA_PELEA / 2)
                    .build();

            HistorialData historialData = HistorialData.builder()
                    .victorias(0)
                    .derrotas(0)
                    .empates(0)
                    .totalPeleas(0)
                    .rachaActual(0)
                    .rachaHistorica(0)
                    .build();

            Peleador peleador = Peleador.builder()
                    .nombre(dtoPeleador.nombre())
                    .apodo(dtoPeleador.apodo())
                    .genero(dtoPeleador.genero())
                    .actividadData(actividadData)
                    .fisicoData(fisicoData)
                    .historialData(historialData)
                    .estadoActividad(true)
                    .build();

            peleadorRepository.save(peleador);

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private void registrarPeleadorExperiencia(DtoPeleador dtoPeleador) throws Exception{
        try {
            CategoriaPeso categoriaPeso = CategoriaPeso.calcularTipoPeso(dtoPeleador.peso());

            ActividadData actividadData = ActividadData.builder()
                    .ultimaPelea(dtoPeleador.ultimaPelea())
                    .duracionPromedioEnPelea(dtoPeleador.duracionPromedioEnPelea())
                    .build();

            FisicoData fisicoData = FisicoData.builder()
                    .peso(dtoPeleador.peso())
                    .altura(dtoPeleador.altura())
                    .alcance(dtoPeleador.alcance())
                    .edad(dtoPeleador.edad())
                    .categoriaPeso(categoriaPeso)
                    .build();

            int totalPeleas = dtoPeleador.victorias() + dtoPeleador.derrotas() + dtoPeleador.empates();

            HistorialData historialData = HistorialData.builder()
                    .victorias(dtoPeleador.victorias())
                    .derrotas(dtoPeleador.derrotas())
                    .empates(dtoPeleador.empates())
                    .totalPeleas(totalPeleas)
                    .rachaActual(dtoPeleador.rachaActual())
                    .rachaHistorica(dtoPeleador.rachaHistorica())
                    .build();

            Peleador peleador = Peleador.builder()
                    .nombre(dtoPeleador.nombre())
                    .apodo(dtoPeleador.apodo())
                    .genero(dtoPeleador.genero())
                    .actividadData(actividadData)
                    .fisicoData(fisicoData)
                    .historialData(historialData)
                    .estadoActividad(true)
                    .build();

            peleadorRepository.save(peleador);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<Peleador> obtenerPeleadores() throws Exception{
        try {
            return peleadorRepository.findAll();
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
