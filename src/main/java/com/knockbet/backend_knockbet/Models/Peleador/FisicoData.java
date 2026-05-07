package com.knockbet.backend_knockbet.Models.Peleador;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Entity
@Table(name = "FisicoDatas")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FisicoData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "peso")
    private int peso;

    @Column(name = "altura")
    private double altura;

    @Column(name = "alcance")
    private double alcance;

    @Column(name = "edad")
    private int edad;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoriaPeso")
    private CategoriaPeso categoriaPeso;

}
