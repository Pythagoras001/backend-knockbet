package com.knockbet.backend_knockbet.Controllers;

import com.knockbet.backend_knockbet.Models.EstrucApuesta.Apuesta;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.UserApuesta;
import com.knockbet.backend_knockbet.Models.dto.DtoUserApuesta;
import com.knockbet.backend_knockbet.Services.ApuestaService;
import com.knockbet.backend_knockbet.Services.UserApuestaService;
import jakarta.validation.Valid;
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
    private final UserApuestaService userApuestaService;

    @GetMapping
    public ResponseEntity<?> obtenerApuestas() throws Exception{
        List<Apuesta> apuestas = apuestaService.obtenerApuestas();
        return ResponseEntity.ok(apuestas);
    }

    @GetMapping
    @RequestMapping("/user")
    public ResponseEntity<?> obtenerTodasUserApuestas() throws Exception{
        List<UserApuesta> userApuestas = userApuestaService.obtenerTodasUserApuestas();
        return ResponseEntity.ok(userApuestas);
    }

    @PostMapping
    @RequestMapping("/{fightId}")
    public ResponseEntity<?> publicarApuesta(@PathVariable UUID fightId) throws Exception{
        apuestaService.registrarApuesta(fightId);
        return ResponseEntity.noContent().build();

    }

    @PostMapping
    @RequestMapping("/stake")
    public ResponseEntity<?> apostar(@Valid @RequestBody DtoUserApuesta dtoUserApuesta) throws Exception{
        userApuestaService.apostarPorGanador(dtoUserApuesta);
        return ResponseEntity.noContent().build();
    }


}
