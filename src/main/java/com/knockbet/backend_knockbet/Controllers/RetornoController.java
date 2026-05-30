package com.knockbet.backend_knockbet.Controllers;

import com.knockbet.backend_knockbet.Models.EstrucPagoApuesta.Factura;
import com.knockbet.backend_knockbet.Models.EstrucPagoApuesta.Retorno;
import com.knockbet.backend_knockbet.Models.dto.DtoPago;
import com.knockbet.backend_knockbet.Services.RetornoService;
import jakarta.validation.Valid;
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
        List<Retorno> retornoList = retornoService.obtnerRetornos();
        return ResponseEntity.ok(retornoList);
    }

    @GetMapping
    @RequestMapping("/bill")
    public ResponseEntity<?> obtenerFacturas() throws Exception{
        List<Factura> facturas = retornoService.obtenerFacturas();
        return ResponseEntity.ok(facturas);
    }

    @PostMapping
    public ResponseEntity<?> pagarRetorno(@Valid @RequestBody DtoPago dtoPago) throws Exception{
        retornoService.pagarApuesta(dtoPago);
        return ResponseEntity.noContent().build();
    }





}
