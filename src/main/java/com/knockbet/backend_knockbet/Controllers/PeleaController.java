package com.knockbet.backend_knockbet.Controllers;

import com.knockbet.backend_knockbet.Models.dto.DtoEditPelea;
import com.knockbet.backend_knockbet.Models.dto.DtoPelea;
import com.knockbet.backend_knockbet.Services.PeleaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fight")
@AllArgsConstructor
public class PeleaController {

    private final PeleaService peleaService;

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

    @PutMapping("/edit")
    public ResponseEntity<?> editarPelea(@RequestBody DtoEditPelea dtoEditPelea){
        try {
            peleaService.editarPelea(dtoEditPelea);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible Editar el peleador");
        }
    }

}
