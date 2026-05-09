package com.knockbet.backend_knockbet.Repository;

import com.knockbet.backend_knockbet.Models.EstrucApuesta.UserApuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserApuestaRepository extends JpaRepository<UserApuesta, UUID> {
}
