package com.knockbet.backend_knockbet.Repository;

import com.knockbet.backend_knockbet.Models.EstrucApuesta.Apuesta;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Pelea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApuestaRepository extends JpaRepository<Apuesta, UUID> {
    Apuesta findByPelea(Pelea pelea);
}
