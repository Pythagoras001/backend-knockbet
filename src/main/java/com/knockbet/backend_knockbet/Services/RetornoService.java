package com.knockbet.backend_knockbet.Services;


import com.knockbet.backend_knockbet.Events.GanarApuestaEvent;
import com.knockbet.backend_knockbet.Events.PerderApuestaEvent;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.ResultadoCuota;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.UserApuesta;
import com.knockbet.backend_knockbet.Models.EstrucPagoApuesta.EstadoRetorno;
import com.knockbet.backend_knockbet.Models.EstrucPagoApuesta.Factura;
import com.knockbet.backend_knockbet.Models.EstrucPagoApuesta.Retorno;
import com.knockbet.backend_knockbet.Models.dto.DtoPago;
import com.knockbet.backend_knockbet.Repository.FacturaRepository;
import com.knockbet.backend_knockbet.Repository.RetornoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RetornoService {

    private final RetornoRepository retornoRepository;
    private final FacturaRepository facturaRepository;

    private final UserApuestaService userApuestaService;
    private final PresupuestoService presupuestoService;
    private final EmailService emailService;

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
                    emailService.enviarVictoriaApuestaMail(userApuesta);
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
                            emailService.enviarPerdidaApuestaMail(apuestaPerdida);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                );

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void pagarApuesta(DtoPago dtoPago) throws Exception{
        try {
            Retorno retorno = obtenerRetornoId(dtoPago.idRetorno());
            if (retorno.getEstadoRetorno() != EstadoRetorno.PENDIENTE) throw new Exception("No se puede pagar un retorno pagado");

            retorno.setEstadoRetorno(EstadoRetorno.PAGADO);

            Factura factura = Factura.builder()
                    .retorno(retorno)
                    .fechaPago(LocalDateTime.now())
                    .metodoPago(dtoPago.metodoPago())
                    .build();

            facturaRepository.save(factura);

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

    public Retorno obtenerRetornoId(UUID id) throws Exception{
        try {
            return retornoRepository.findById(id)
                    .orElseThrow(() -> new Exception("Retorno no encontrado: " + id));
        }catch (Exception e){
            throw new Exception(e);
        }
    }


}