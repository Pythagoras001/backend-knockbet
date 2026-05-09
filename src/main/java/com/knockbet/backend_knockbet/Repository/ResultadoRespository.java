package com.knockbet.backend_knockbet.Repository;

import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResultadoRespository extends JpaRepository<Resultado, UUID> {
}
