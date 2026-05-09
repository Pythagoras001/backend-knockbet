package com.knockbet.backend_knockbet.Services;

import com.knockbet.backend_knockbet.Events.GanarApuestaEvent;
import com.knockbet.backend_knockbet.Events.InicioPeleaEvent;
import com.knockbet.backend_knockbet.Events.PerderApuestaEvent;
import com.knockbet.backend_knockbet.Events.ResultadoPeleaEvent;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.Apuesta;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.Cuota;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.EstadoApuesta;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.ResultadoCuota;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.EstadoPelea;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Pelea;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Resultado;
import com.knockbet.backend_knockbet.Models.EstrucPredictSystem.PrediccionResultadoPelea;
import com.knockbet.backend_knockbet.Models.EstrucPredictSystem.TasaRecomendada;
import com.knockbet.backend_knockbet.Models.Peleador.Peleador;
import com.knockbet.backend_knockbet.Repository.ApuestaRepository;
import com.knockbet.backend_knockbet.Repository.PeleaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ApuestaService {

    private final ApuestaRepository apuestaRepository;
    private final PeleaRepository peleaRepository;
    private final ApplicationEventPublisher eventPublisher;

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

    @EventListener
    public void cerrarApuesta(InicioPeleaEvent event) throws Exception{
        try {
            Optional<Apuesta> apuestaOpt = buscarApuestaPorPelea(event.pelea());
            if (apuestaOpt.isEmpty()) return;

            Apuesta apuestaEncontrada = apuestaOpt.get();

            if (apuestaEncontrada.getActivo() != EstadoApuesta.ABIERTA) throw new Exception("Para finalizar una apuesta debe estar disponible");
            apuestaEncontrada.setActivo(EstadoApuesta.CERRADA);

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @EventListener
    @Transactional
    public void finalizarApuesta(ResultadoPeleaEvent event) throws Exception{
        try {
            Optional<Apuesta> apuestaOpt = buscarApuestaPorPelea(event.resultado().getPelea());
            if (apuestaOpt.isEmpty()){
                System.out.println("no se encontro la apueta para cerrarla");
                return;
            }
            Apuesta apuestaEncontrada = apuestaOpt.get();

            if (apuestaEncontrada.getActivo() != EstadoApuesta.CERRADA) throw new Exception("Para finalizar una puesta primero debe estar cerrada");
            apuestaEncontrada.setActivo(EstadoApuesta.FINALIZADA);

            anunciarGanadoresYPerdedores(apuestaEncontrada, event.resultado());

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private void anunciarGanadoresYPerdedores(Apuesta apuesta, Resultado resultado) throws Exception{
        try {
            Peleador ganador = resultado.getGanador();
            Peleador perdedor = resultado.getPerdedor();

            Cuota cuotaA = apuesta.getCuotaPeleadorA();
            Cuota cuotaB = apuesta.getCuotaPeleadorB();
            Cuota cuotaEmpate = apuesta.getCuotaEmpate();

            boolean esEmpate = (ganador == null && perdedor == null);
            if (!esEmpate && (ganador == null || perdedor == null)) throw new Exception("Resultado invalido: ganador/perdedor incompletos");

            Cuota cuotaGanadora;
            if (esEmpate) {
                cuotaGanadora = cuotaEmpate;
            } else if (cuotaA.getPeleador() != null && cuotaA.getPeleador().equals(ganador)) {
                cuotaGanadora = cuotaA;
            } else if (cuotaB.getPeleador() != null && cuotaB.getPeleador().equals(ganador)) {
                cuotaGanadora = cuotaB;
            } else {
                throw new Exception("El ganador no coincide con ninguna cuota de la apuesta");
            }

            actualizarCuota(cuotaA, cuotaA == cuotaGanadora ? ResultadoCuota.GANADA : ResultadoCuota.PERDIDA);
            actualizarCuota(cuotaB, cuotaB == cuotaGanadora ? ResultadoCuota.GANADA : ResultadoCuota.PERDIDA);
            actualizarCuota(cuotaEmpate, cuotaEmpate == cuotaGanadora ? ResultadoCuota.GANADA : ResultadoCuota.PERDIDA);

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private void actualizarCuota(Cuota cuota, ResultadoCuota resultadoCuota) throws Exception{
        try {
            cuota.setResultadoCuota(resultadoCuota);

            if (resultadoCuota == ResultadoCuota.GANADA){
                eventPublisher.publishEvent(new GanarApuestaEvent(cuota));
            } else if (resultadoCuota == ResultadoCuota.PERDIDA){
                eventPublisher.publishEvent(new PerderApuestaEvent(cuota));
            }

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public Apuesta buscarApuesta(UUID idApuesta) throws Exception{
        try {
            return apuestaRepository.findById(idApuesta)
                    .orElseThrow(() -> new Exception("Apuesta no Encontrada"));
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public Optional<Apuesta> buscarApuestaPorPelea(Pelea pelea) throws Exception{
        return Optional.ofNullable(apuestaRepository.findByPelea(pelea));
    }

}
