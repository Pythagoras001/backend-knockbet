package com.knockbet.backend_knockbet.Controllers;

import com.knockbet.backend_knockbet.Models.EstrucPresupuesto.Presupuesto;
import com.knockbet.backend_knockbet.Models.dto.DtoDeposito;
import com.knockbet.backend_knockbet.Services.PresupuestoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget")
@AllArgsConstructor
public class PresupuestoController {

    private final PresupuestoService presupuestoService;

    @GetMapping
    public ResponseEntity<?> obtenerPresupuesto() throws Exception{
        Presupuesto presupuesto =  presupuestoService.obtenerPresupuesto();
        return ResponseEntity.ok(presupuesto);
    }

    @PostMapping
    public ResponseEntity<?> agregarPresupuesto(@Valid @RequestBody DtoDeposito dtoDeposito) throws Exception{
        presupuestoService.incrementarPresupuesto(dtoDeposito);
        return ResponseEntity.noContent().build();
    }
}
