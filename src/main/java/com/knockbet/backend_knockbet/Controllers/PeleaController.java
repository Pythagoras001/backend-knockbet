package com.knockbet.backend_knockbet.Controllers;

import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Pelea;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Resultado;
import com.knockbet.backend_knockbet.Models.dto.DtoEditPelea;
import com.knockbet.backend_knockbet.Models.dto.DtoPelea;
import com.knockbet.backend_knockbet.Models.dto.DtoResultadoApuesta;
import com.knockbet.backend_knockbet.Services.PeleaService;
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
        try {
            List<Pelea> peleaList = peleaService.obtenerPeleas();
            return ResponseEntity.ok(peleaList);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las peleas");
        }
    }

    @GetMapping("/free")
    public ResponseEntity<?> obtenerPeleasSinApuesta() throws Exception{
        try {
            List<Pelea> peleaList = peleaService.obtenerPeleasLibres();
            return ResponseEntity.ok(peleaList);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los peleadores");
        }
    }

    @GetMapping
    @RequestMapping("/results")
    public ResponseEntity<?> obtenerResultadosPelea() throws Exception{
        try {
            List<Resultado> resultados = peleaService.obtenerResultados();
            return ResponseEntity.ok(resultados);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los Resultados");
        }
    }

    @PostMapping
    public ResponseEntity<?> registrarPelea(@RequestBody DtoPelea dtoPelea){
        try {
            peleaService.registrarPelea(dtoPelea);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping
    @RequestMapping("{fightId}/start")
    public ResponseEntity<?> iniciarPelea(@PathVariable UUID fightId){
        try {
            peleaService.iniciarPelea(fightId);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping
    @RequestMapping("/result")
    public ResponseEntity<?> finalizarPelea(@RequestBody DtoResultadoApuesta dtoResultadoApuesta) throws Exception{
        try {
            peleaService.finalizarPelea(dtoResultadoApuesta);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editarPelea(@RequestBody DtoEditPelea dtoEditPelea){
        try {
            peleaService.editarPelea(dtoEditPelea);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible Editar la pelea");
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> cancelarPelea(@PathVariable UUID id){
        try {
            peleaService.cancelarPelea(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible deshabilitar el peleador");
        }
    }


}
