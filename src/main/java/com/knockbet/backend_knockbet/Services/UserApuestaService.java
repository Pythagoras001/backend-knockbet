package com.knockbet.backend_knockbet.Services;

import com.knockbet.backend_knockbet.Events.NuevaUserApuesta;
import com.knockbet.backend_knockbet.Models.EstrucApostador.Apostador;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.Apuesta;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.Cuota;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.EstadoApuesta;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.UserApuesta;
import com.knockbet.backend_knockbet.Models.dto.DtoUserApuesta;
import com.knockbet.backend_knockbet.Reglas.MetricasDeNegocio;
import com.knockbet.backend_knockbet.Repository.UserApuestaRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserApuestaService {

    private final UserApuestaRepository userApuestaRepository;
    private final ApplicationEventPublisher eventPublisher;

    private final ApuestaService apuestaService;
    private final PresupuestoService presupuestoService;

    public void apostarPorGanador(DtoUserApuesta dtoUserApuesta) throws Exception{
        try {
            Apuesta apuestaEncontrada = apuestaService.buscarApuesta(dtoUserApuesta.apuesta());
            if (apuestaEncontrada.getActivo() != EstadoApuesta.ABIERTA) throw new Exception("La apuesta no esta disponible");

            Cuota cuota = getCuota(dtoUserApuesta, apuestaEncontrada);

            UserApuesta userApuesta = UserApuesta.builder()
                    .apostador(new Apostador(dtoUserApuesta.nombreApostador(), dtoUserApuesta.cedula(), dtoUserApuesta.celular(), "thomas200719@gmail.com"))
                    .apuesta(apuestaEncontrada)
                    .ganadorEsperado(cuota)
                    .valorApostado(dtoUserApuesta.valorApostado())
                    .rendimientoGananciaAsociada(cuota.getCuotaGananciaActual())
                    .build();

            // CON ESTO CONFIRMAMOS Q NO SALGA DEL LIMITE
            if (!esPagable(userApuesta)) throw new Exception("La apuesta supera el presupuesto");

            userApuesta.getGanadorEsperado().getEstadisticaAsociada().actulizarEstadisticaCuota(userApuesta);
            presupuestoService.actulizarPresupuestoPorApuesta(userApuesta);

            userApuestaRepository.save(userApuesta);
            eventPublisher.publishEvent(new NuevaUserApuesta(userApuesta));

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private boolean esPagable(UserApuesta userApuesta) throws Exception{
        try {
            return (userApuesta.getTotalGananciaPosible() - userApuesta.getValorApostado()
                    + userApuesta.getGanadorEsperado().getEstadisticaAsociada().getPagoTotalPorVictoria()
                    < presupuestoService.verPresupuestoTotalActual() * MetricasDeNegocio.LIMITE_EXP);

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<UserApuesta> buscarUserApuestasInscritas(Cuota cuota) throws Exception{
        try {
            return userApuestaRepository.findAllByGanadorEsperado(cuota)
                    .orElseThrow(() -> new RuntimeException("No se encontraron apuestas en la cuota de apuesta: " + cuota.getId()));
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<UserApuesta> obtenerTodasUserApuestas() throws Exception{
        try {
            return userApuestaRepository.findAll();
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private Cuota getCuota(DtoUserApuesta dtoUserApuesta, Apuesta apuestaEncontrada) throws Exception {
        Cuota cuota = (apuestaEncontrada.getCuotaPeleadorA().getId().equals(dtoUserApuesta.ganadorEsperado())) ?
                apuestaEncontrada.getCuotaPeleadorA() : (apuestaEncontrada.getCuotaPeleadorB().getId().equals(dtoUserApuesta.ganadorEsperado()))?
                apuestaEncontrada.getCuotaPeleadorB() : (apuestaEncontrada.getCuotaEmpate().getId().equals(dtoUserApuesta.ganadorEsperado()))?
                apuestaEncontrada.getCuotaEmpate() : null;

        if (cuota == null) throw new Exception("Apuesta invalida cuota no encontrada");

        return cuota;
    }
}
