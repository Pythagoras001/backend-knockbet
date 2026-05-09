package com.knockbet.backend_knockbet.Services;

import com.knockbet.backend_knockbet.Models.EstrucApuesta.Apuesta;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.Cuota;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.EstadoPelea;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Pelea;
import com.knockbet.backend_knockbet.Models.EstrucPredictSystem.PrediccionResultadoPelea;
import com.knockbet.backend_knockbet.Models.EstrucPredictSystem.TasaRecomendada;
import com.knockbet.backend_knockbet.Repository.ApuestaRepository;
import com.knockbet.backend_knockbet.Repository.PeleaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ApuestaService {

    private final ApuestaRepository apuestaRepository;
    private final PeleaRepository peleaRepository;

    private final ScoreService scoreService;
    private final GestorCuotasService gestorCuotasService;

    public void registrarApuesta(UUID fightId) throws Exception{
        try {
            Pelea peleaEncontrada = peleaRepository.findById(fightId)
                    .orElseThrow(() -> new Exception("Pelea no encontrada: " + fightId));

            if (apuestaRepository.findByPelea(peleaEncontrada) != null) throw new Exception("La pelea ya cuenta con una apuesta asociada");
            if (peleaEncontrada.getEstadoPelea() != EstadoPelea.PROGRAMADA) throw new Exception("Para asignar una apuesta a una pelea solo puede estar programada");

            PrediccionResultadoPelea prediccionResultadoPelea = scoreService.calcularProbabilidadDeVictoria(peleaEncontrada);
            TasaRecomendada tasaDeRendimientoRecomendada = gestorCuotasService.calcularTasaDeRendimientoInicial(prediccionResultadoPelea);

            Apuesta nuevaApuesta = Apuesta.builder()
                    .pelea(peleaEncontrada)
                    .prediccionResultadoPelea(prediccionResultadoPelea)
                    .cuotaPeleadorA(new Cuota(peleaEncontrada.getPeleadorA(), tasaDeRendimientoRecomendada.tasaRedimientoA()))
                    .cuotaPeleadorB(new Cuota(peleaEncontrada.getPeleadorB(), tasaDeRendimientoRecomendada.tasaRendimientoB()))
                    .cuotaEmpate(new Cuota(null, tasaDeRendimientoRecomendada.tasaRendimientoEmpate()))
                    .build();

            apuestaRepository.save(nuevaApuesta);

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<Apuesta> obtenerApuestas()throws Exception{
        try {
            return apuestaRepository.findAll();
        }catch (Exception e){
            throw new Exception(e);
        }
    }




}
