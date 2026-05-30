package com.knockbet.backend_knockbet.Controllers;

import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Pelea;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Resultado;
import com.knockbet.backend_knockbet.Models.dto.DtoEditPelea;
import com.knockbet.backend_knockbet.Models.dto.DtoPelea;
import com.knockbet.backend_knockbet.Models.dto.DtoResultadoApuesta;
import com.knockbet.backend_knockbet.Services.PeleaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fight")
@AllArgsConstructor
public class PeleaController {

    private final PeleaService peleaService;

    @GetMapping
    public ResponseEntity<?> obtnerPeleas() throws Exception{
        List<Pelea> peleaList = peleaService.obtenerPeleas();
        return ResponseEntity.ok(peleaList);
    }

    @GetMapping("/free")
    public ResponseEntity<?> obtenerPeleasSinApuesta() throws Exception{
        List<Pelea> peleaList = peleaService.obtenerPeleasLibres();
        return ResponseEntity.ok(peleaList);
    }

    @GetMapping
    @RequestMapping("/results")
    public ResponseEntity<?> obtenerResultadosPelea() throws Exception{
        List<Resultado> resultados = peleaService.obtenerResultados();
        return ResponseEntity.ok(resultados);
    }

    @PostMapping
    public ResponseEntity<?> registrarPelea(@Valid @RequestBody DtoPelea dtoPelea) throws Exception{
        peleaService.registrarPelea(dtoPelea);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @RequestMapping("{fightId}/start")
    public ResponseEntity<?> iniciarPelea(@PathVariable UUID fightId) throws Exception{
        peleaService.iniciarPelea(fightId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @RequestMapping("/result")
    public ResponseEntity<?> finalizarPelea(@Valid @RequestBody DtoResultadoApuesta dtoResultadoApuesta) throws Exception{
        peleaService.finalizarPelea(dtoResultadoApuesta);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editarPelea(@Valid @RequestBody DtoEditPelea dtoEditPelea) throws Exception{
        peleaService.editarPelea(dtoEditPelea);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> cancelarPelea(@PathVariable UUID id) throws Exception{
        peleaService.cancelarPelea(id);
        return ResponseEntity.noContent().build();

    }


}
