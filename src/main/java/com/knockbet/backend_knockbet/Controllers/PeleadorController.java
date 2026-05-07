package com.knockbet.backend_knockbet.Controllers;

import com.knockbet.backend_knockbet.Models.Peleador.Peleador;
import com.knockbet.backend_knockbet.Models.dto.DtoPeleador;
import com.knockbet.backend_knockbet.Services.PeleadorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fighters")
@AllArgsConstructor
public class PeleadorController {

    private final PeleadorService peleadorService;

    @GetMapping
    public ResponseEntity<?> obtenerPeleadores() throws Exception{
        try {
            List<Peleador> peleadores = peleadorService.obtenerPeleadores();
            return ResponseEntity.ok(peleadores);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los peleadores");
        }
    }

    @PostMapping
    public ResponseEntity<?> registrarPeleador(@RequestBody DtoPeleador dtoPeleador) throws Exception{
        try {
            peleadorService.registrarPeleador(dtoPeleador);
            return ResponseEntity.noContent().build();

        }catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> cambiarEstadoDeActividad(@PathVariable UUID id){
        try {
            peleadorService.cambiarEstadoDeActividad(id);
            return ResponseEntity.ok().build();

        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible deshabilitar el peleador");
        }
    }



}
