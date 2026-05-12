package com.knockbet.backend_knockbet.Repository;

import com.knockbet.backend_knockbet.Models.EstrucPagoApuesta.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FacturaRepository extends JpaRepository<Factura, UUID> {
}
