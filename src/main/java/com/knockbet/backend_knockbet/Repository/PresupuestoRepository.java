package com.knockbet.backend_knockbet.Repository;

import com.knockbet.backend_knockbet.Models.EstrucPresupuesto.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
}
