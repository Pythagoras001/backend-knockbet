package com.knockbet.backend_knockbet.Services;

import com.knockbet.backend_knockbet.Models.EstrucEncuentro.EstadoPelea;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Pelea;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Ubicacion;
import com.knockbet.backend_knockbet.Models.Peleador.Peleador;
import com.knockbet.backend_knockbet.Models.dto.DtoPelea;
import com.knockbet.backend_knockbet.Repository.PeleaRepository;
import com.knockbet.backend_knockbet.Repository.PeleadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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



}
