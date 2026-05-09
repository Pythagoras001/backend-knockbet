package com.knockbet.backend_knockbet.Services;

import com.knockbet.backend_knockbet.Events.InicioPeleaEvent;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.Apuesta;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.EstadoApuesta;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.EstadoPelea;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Pelea;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Ubicacion;
import com.knockbet.backend_knockbet.Models.dto.DtoEditPelea;
import com.knockbet.backend_knockbet.Models.dto.DtoPelea;
import com.knockbet.backend_knockbet.Repository.ApuestaRepository;
import com.knockbet.backend_knockbet.Repository.PeleaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PeleaService {

    private final PeleaRepository peleaRepository;
    private final ApuestaRepository apuestaRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final PeleadorService peleadorService;

    public void registrarPelea(DtoPelea dtoPelea) throws Exception{
        try {
            Pelea peleaProgramada = Pelea.builder()
                    .tituloPelea(dtoPelea.nombrePelea())
                    .estadoPelea(EstadoPelea.PROGRAMADA)
                    .fechaPelea(dtoPelea.horaIncio())
                    .peleadorA(peleadorService.obtenerPeladorId(dtoPelea.idPeleadorA()))
                    .peleadorB(peleadorService.obtenerPeladorId(dtoPelea.idPeleadorB()))
                    .ubicacion(new Ubicacion(dtoPelea.direccion(), dtoPelea.descripcion()))
                    .build();

            peleaRepository.save(peleaProgramada);

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void editarPelea(DtoEditPelea dtoEditPelea) throws Exception{
        try {
            Pelea pelea = obtenerPeleaId(dtoEditPelea.idPelea());

            if (pelea.getEstadoPelea() != EstadoPelea.PROGRAMADA) throw new Exception("Para editar una pelea su estado debe ser Programada");

            pelea.setTituloPelea(dtoEditPelea.nombrePelea());
            pelea.setFechaPelea(dtoEditPelea.horaIncio());
            pelea.setUbicacion(new Ubicacion(dtoEditPelea.direccion(), dtoEditPelea.descripcion()));

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void cancelarPelea(UUID id) throws Exception{
        try {
            Pelea pelea = obtenerPeleaId(id);
            Apuesta apuestaAsociada = apuestaRepository.findByPelea(pelea);

            if (pelea.getEstadoPelea() == EstadoPelea.CANCELADA) throw new Exception("No se puede cancelar una pelea Finalizada");

            if (apuestaAsociada == null){
                pelea.setEstadoPelea(EstadoPelea.CANCELADA);
                return;
            }

            if (apuestaAsociada.getActivo() != EstadoApuesta.CANCELADA) throw new Exception("Se debe cancelar la apuesta asocicada a la pelea primero");
            pelea.setEstadoPelea(EstadoPelea.CANCELADA);

        }catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public void iniciarPelea(UUID idPelea) throws Exception{
        try {
            Pelea peleaEncontrada = obtenerPeleaId(idPelea);
            if (peleaEncontrada.getEstadoPelea() != EstadoPelea.PROGRAMADA) throw new Exception("Para iniciar una pelea debe estar programada");
            peleaEncontrada.setEstadoPelea(EstadoPelea.EN_DUELO);
            eventPublisher.publishEvent(new InicioPeleaEvent(peleaEncontrada));

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public Pelea obtenerPeleaId(UUID idPelea) throws Exception{
        try {
            return peleaRepository.findById(idPelea)
                    .orElseThrow(() -> new Exception("Pelea no encontrada: " + idPelea));
        }catch (Exception e){
            throw new Exception(e);
        }
    }

}
