package com.knockbet.backend_knockbet.Controllers;

import com.knockbet.backend_knockbet.Models.EstrucApuesta.Apuesta;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.UserApuesta;
import com.knockbet.backend_knockbet.Services.ApuestaService;
import com.knockbet.backend_knockbet.Services.UserApuestaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bet")
@AllArgsConstructor
public class ApuestaController {

    private final ApuestaService apuestaService;

    @GetMapping
    public ResponseEntity<?> obtenerApuestas() throws Exception{
        try {
            List<Apuesta> apuestas = apuestaService.obtenerApuestas();
            return ResponseEntity.ok(apuestas);
        }catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping
    @RequestMapping("/{fightId}")
    public ResponseEntity<?> publicarApuesta(@PathVariable UUID fightId) throws Exception{
        try {
            apuestaService.registrarApuesta(fightId);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }



}
