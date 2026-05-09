package com.knockbet.backend_knockbet.Services;


import com.knockbet.backend_knockbet.Events.GanarApuestaEvent;
import com.knockbet.backend_knockbet.Events.PerderApuestaEvent;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.ResultadoCuota;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.UserApuesta;
import com.knockbet.backend_knockbet.Models.EstrucPagoApuesta.Retorno;
import com.knockbet.backend_knockbet.Repository.RetornoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RetornoService {

    private final RetornoRepository retornoRepository;
    private final UserApuestaService userApuestaService;
    private final PresupuestoService presupuestoService;

    @EventListener
    @Transactional
    public void registrarApuestasGanadoras(GanarApuestaEvent event) throws Exception{
        try {
            if (event.cuota().getResultadoCuota() != ResultadoCuota.GANADA) throw new Exception("No se puede registrar retornos de una apuesta q no se ha ganado");

            List<UserApuesta> apuestasGanadoras = userApuestaService.buscarUserApuestasInscritas(event.cuota());

            List<Retorno> retornos = apuestasGanadoras.stream().map(userApuesta -> {
                try {
                    presupuestoService.actulizarPresupuestoPorApuesta(userApuesta);
                    Retorno retorno = new Retorno(userApuesta);
                    return retorno;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).toList();

            retornoRepository.saveAll(retornos);

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @EventListener
    @Transactional
    public void registrarApuestasPerdedoras(PerderApuestaEvent event) throws Exception{
        try {
            userApuestaService
                .buscarUserApuestasInscritas(event.cuota())
                .forEach(apuestaPerdida ->
                    {
                        try {
                            presupuestoService.actulizarPresupuestoPorApuesta(apuestaPerdida);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                );

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<Retorno> obtnerRetornos() throws Exception{
        try {
            return retornoRepository.findAll();
        }catch (Exception e){
            throw new Exception(e);
        }
    }

}