package com.knockbet.backend_knockbet.Controllers;

import com.knockbet.backend_knockbet.Models.dto.DtoPelea;
import com.knockbet.backend_knockbet.Services.PeleaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fight")
@AllArgsConstructor
public class PeleaController {

    private final PeleaService peleaService;

    @PostMapping
    public ResponseEntity<?> registrarPelea(@RequestBody DtoPelea dtoPelea) throws Exception{
        try {
            peleaService.registrarPelea(dtoPelea);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

}
