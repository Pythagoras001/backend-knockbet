package com.knockbet.backend_knockbet.Repository;

import com.knockbet.backend_knockbet.Models.EstrucPagoApuesta.Retorno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RetornoRepository extends JpaRepository<Retorno, UUID> {
}
