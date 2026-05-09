package com.knockbet.backend_knockbet.Services;

import com.knockbet.backend_knockbet.Models.EstrucEncuentro.EstadoPelea;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Pelea;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Ubicacion;
import com.knockbet.backend_knockbet.Models.Peleador.Peleador;
import com.knockbet.backend_knockbet.Models.dto.DtoEditPelea;
import com.knockbet.backend_knockbet.Models.dto.DtoPelea;
import com.knockbet.backend_knockbet.Repository.PeleaRepository;
import com.knockbet.backend_knockbet.Repository.PeleadorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PeleaService {

    private final PeleaRepository peleaRepository;

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

    public Pelea obtenerPeleaId(UUID idPelea) throws Exception{
        try {
            return peleaRepository.findById(idPelea)
                    .orElseThrow(() -> new Exception("Pelea no encontrada: " + idPelea));
        }catch (Exception e){
            throw new Exception(e);
        }
    }

}
