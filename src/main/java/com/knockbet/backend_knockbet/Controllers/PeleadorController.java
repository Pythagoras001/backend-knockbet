package com.knockbet.backend_knockbet.Controllers;

import com.knockbet.backend_knockbet.Models.Peleador.Peleador;
import com.knockbet.backend_knockbet.Models.dto.DtoEditPeleador;
import com.knockbet.backend_knockbet.Models.dto.DtoPeleador;
import com.knockbet.backend_knockbet.Services.PeleadorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fighters")
@AllArgsConstructor
public class PeleadorController {

    private final PeleadorService peleadorService;

    @GetMapping
    public ResponseEntity<?> obtenerPeleadores() throws Exception{
        List<Peleador> peleadores = peleadorService.obtenerPeleadores();
        return ResponseEntity.ok(peleadores);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registrarPeleador(@Valid @ModelAttribute DtoPeleador dtoPeleador, @RequestParam MultipartFile img) throws Exception{
        peleadorService.registrarPeleador(dtoPeleador, img);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> cambiarEstadoDeActividad(@PathVariable UUID id) throws Exception{
        peleadorService.cambiarEstadoDeActividad(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editarPeleador(@Valid @RequestBody DtoEditPeleador dtoEditPeleador) throws Exception {
        peleadorService.actulizarDatosPeleador(dtoEditPeleador);
        return ResponseEntity.ok().build();
    }

}
