package com.knockbet.backend_knockbet.Repository;

import com.knockbet.backend_knockbet.Models.EstrucApuesta.Cuota;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.UserApuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserApuestaRepository extends JpaRepository<UserApuesta, UUID> {
    Optional<List<UserApuesta>> findAllByGanadorEsperado(Cuota ganadorEsperado);

}
