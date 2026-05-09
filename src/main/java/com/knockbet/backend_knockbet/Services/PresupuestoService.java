package com.knockbet.backend_knockbet.Services;

import com.knockbet.backend_knockbet.Models.EstrucApuesta.UserApuesta;
import com.knockbet.backend_knockbet.Models.EstrucPresupuesto.Presupuesto;
import com.knockbet.backend_knockbet.Models.dto.DtoDeposito;
import com.knockbet.backend_knockbet.Repository.PresupuestoRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PresupuestoService {

    private static final Long ID_UNICO = 1L;
    private final PresupuestoRepository presupuestoRepository;

    @PostConstruct
    private void init() {
        if (!presupuestoRepository.existsById(ID_UNICO)) {
            presupuestoRepository.save(new Presupuesto());
        }
    }

    public Presupuesto obtenerPresupuesto()throws Exception{
        try {
            Presupuesto p = presupuestoRepository.findById(ID_UNICO)
                    .orElseThrow(() -> new Exception("No existe el presupuesto"));
            return p;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void incrementarPresupuesto(DtoDeposito dtoDeposito) throws Exception{
        try {
            Presupuesto p = presupuestoRepository.findById(ID_UNICO)
                    .orElseThrow(() -> new Exception("No existe el presupuesto"));
            p.incrementarPresupuesto(dtoDeposito.monto());
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public void actulizarPresupuestoPorApuesta(UserApuesta userApuesta) throws Exception{
        try {
            Presupuesto p = presupuestoRepository.findById(ID_UNICO)
                    .orElseThrow(() -> new Exception("No existe el presupuesto"));
            p.actulizarPresupuestoPorApuesta(userApuesta);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public int verPresupuestoTotalActual() throws Exception{
        try {
            Presupuesto p = presupuestoRepository.findById(ID_UNICO)
                    .orElseThrow(() -> new Exception("No existe el presupuesto"));
            return p.getPresupuestoTotal();
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
