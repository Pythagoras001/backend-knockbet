package com.knockbet.backend_knockbet.Controllers;

import com.knockbet.backend_knockbet.Models.EstrucPagoApuesta.Retorno;
import com.knockbet.backend_knockbet.Models.dto.DtoPago;
import com.knockbet.backend_knockbet.Services.RetornoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pay")
@AllArgsConstructor
public class RetornoController {

    private final RetornoService retornoService;

    @GetMapping
    public ResponseEntity<?> obtnerRetornos() throws Exception{
        try {
            List<Retorno> retornoList = retornoService.obtnerRetornos();
            return ResponseEntity.ok(retornoList);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener Retornos");
        }
    }

    @PostMapping
    public ResponseEntity<?> pagarRetorno(@RequestBody DtoPago dtoPago){
        try {
            retornoService.pagarApuesta(dtoPago);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }





}
