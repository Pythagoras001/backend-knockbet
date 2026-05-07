package com.knockbet.backend_knockbet.Services;

import com.knockbet.backend_knockbet.Models.EstrucEncuentro.EstadoPelea;
import com.knockbet.backend_knockbet.Models.Peleador.Peleador;
import com.knockbet.backend_knockbet.Repository.PeleaRepository;
import com.knockbet.backend_knockbet.Repository.PeleadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PeleaService {

    private final PeleaRepository peleaRepository;

    public boolean tienePeleasProgramadas(Peleador peleador) throws Exception{
        try {
            return peleaRepository.existePeleaActiva(peleador,List.of(EstadoPelea.PROGRAMADA,EstadoPelea.EN_DUELO));
        }catch (Exception e){
            throw new Exception(e);
        }
    }


}
