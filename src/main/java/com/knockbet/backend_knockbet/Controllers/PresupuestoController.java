package com.knockbet.backend_knockbet.Controllers;

import com.knockbet.backend_knockbet.Models.EstrucPresupuesto.Presupuesto;
import com.knockbet.backend_knockbet.Models.dto.DtoDeposito;
import com.knockbet.backend_knockbet.Services.PresupuestoService;
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
        try {
            Presupuesto presupuesto =  presupuestoService.obtenerPresupuesto();
            return ResponseEntity.ok(presupuesto);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el presupuesto");
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarPresupuesto(@RequestBody DtoDeposito dtoDeposito) throws Exception{
        try {
            presupuestoService.incrementarPresupuesto(dtoDeposito);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
