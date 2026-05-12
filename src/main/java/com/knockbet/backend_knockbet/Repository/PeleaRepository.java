package com.knockbet.backend_knockbet.Repository;

import com.knockbet.backend_knockbet.Models.EstrucEncuentro.EstadoPelea;
import com.knockbet.backend_knockbet.Models.EstrucEncuentro.Pelea;
import com.knockbet.backend_knockbet.Models.Peleador.Peleador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PeleaRepository extends JpaRepository<Pelea, UUID> {
    @Query("""
        SELECT COUNT(p) > 0
        FROM Pelea p
        WHERE
            (p.peleadorA = :peleador
            OR p.peleadorB = :peleador)
        AND p.estadoPelea IN :estados
    """)
    boolean existePeleaActiva(
            @Param("peleador") Peleador peleador,
            @Param("estados") List<EstadoPelea> estados
    );

    @Query("""
            select p
            from Pelea p
            where p not in (select a.pelea from Apuesta a)
            order by p.fechaPelea desc
            """)
    List<Pelea> findPeleasSinApuesta();

    List<Pelea> findAllByOrderByFechaPeleaDesc();
}
