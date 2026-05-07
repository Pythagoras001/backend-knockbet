package com.knockbet.backend_knockbet.Repository;

import com.knockbet.backend_knockbet.Models.Peleador.Peleador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PeleadorRepository extends JpaRepository<Peleador, UUID> {
}
